package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.model.Word;
import online.comfyplace.chineserevision.repository.WordsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public WordsRepository wordsRepository(@Value("${words.source}") ClassPathResource wordsSource) throws IOException {
        final List<Word> words = FileCopyUtils.copyToString(new InputStreamReader(wordsSource.getInputStream(), StandardCharsets.UTF_8))
                .lines()
                .map(ApplicationConfiguration::parseWordLine)
                .toList();

        validateUniqueness(words, Word::traditionalCharacter);
        validateUniqueness(words, Word::english);

        return new WordsRepository(words);
    }

    private static Word parseWordLine(String line) {
        final String[] parts = line.split(",");
        return new Word(parts[0], parts[1], parts[2]);
    }

    private static void validateUniqueness(final List<Word> dictionary, Function<? super Word, String> groupingFunction) {
        dictionary.stream()
                .collect(Collectors.groupingBy(groupingFunction))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(ApplicationConfiguration::reportDuplicate);
    }

    private static void reportDuplicate(final Map.Entry<String, List<Word>> entry) {
        System.err.println("Duplicate found:");
        for (Word word : entry.getValue()) {
            System.err.println("-> " + word.toString());
        }
    }
}
