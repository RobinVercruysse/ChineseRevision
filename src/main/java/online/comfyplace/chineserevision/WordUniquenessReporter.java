package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.model.Word;
import online.comfyplace.chineserevision.repository.WordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class WordUniquenessReporter implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordUniquenessReporter.class);

    @Autowired
    private WordsRepository wordsRepository;

    @Override
    public void run(ApplicationArguments args) {
        try {
            validateUniqueness(wordsRepository.allWords(), "traditional character", Word::traditionalCharacter);
            validateUniqueness(wordsRepository.allWords(), "English translation", Word::english);
        } catch (Exception exception) {
            LOGGER.error("Failed to report word uniqueness warnings", exception);
        }
    }

    private static void validateUniqueness(final List<Word> dictionary, String fieldName, Function<? super Word, String> groupingFunction) {
        dictionary.stream()
                .collect(Collectors.groupingBy(groupingFunction))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry -> reportDuplicate(fieldName, entry));
    }

    private static void reportDuplicate(String fieldName, final Map.Entry<String, List<Word>> entry) {
        LOGGER.warn("Duplicate {} found: {}", fieldName, entry.getKey());
        for (Word word : entry.getValue()) {
            LOGGER.warn("-> {}", word);
        }
    }
}
