package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.io.DictionaryReader;
import online.comfyplace.chineserevision.model.Word;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChineseRevision {
    public static void main(String[] args) throws IOException {
        System.out.println("Loading dictionary...");
        final List<Word> dictionary;
        try (final DictionaryReader reader = new DictionaryReader("words.csv")) {
            dictionary = reader.readAll();
        }
        System.out.printf("Finished loading dictionary of %d words%n", dictionary.size());
        validateUniqueness(dictionary, Word::getSimplified);
        validateUniqueness(dictionary, Word::getEnglish);
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. English -> Pinyin");
        System.out.println("2. Pinyin -> English");
        System.out.println("3. Pinyin -> Simplified");
        System.out.println("4. Simplified -> Pinyin");
        System.out.println("5. Exit");
        final int revisionMode = scanner.nextInt();
        final Revision revision = new Revision(dictionary);
        while (revision.hasNext()) {
            final Word currentWord = revision.next();
            switch (revisionMode) {
                case 1:
                    System.out.printf("%s -> %s%n", currentWord.getEnglish(), currentWord.getPinyin());
                    break;
                case 2:
                    System.out.printf("%s -> %s%n", currentWord.getPinyin(), currentWord.getEnglish());
                    break;
                case 3:
                    System.out.printf("%s -> %s%n", currentWord.getPinyin(), currentWord.getSimplified());
                    break;
                case 4:
                    System.out.printf("%s -> %s%n", currentWord.getSimplified(), currentWord.getPinyin());
                    break;
                default:
                    break;
            }
        }
    }

    private static void  validateUniqueness(final List<Word> dictionary, Function<? super Word, String> groupingFunction) {
        dictionary.stream()
                .collect(Collectors.groupingBy(groupingFunction))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(ChineseRevision::reportDuplicate);
    }

    private static void reportDuplicate(final Map.Entry<String, List<Word>> entry) {
        System.err.println("Duplicate found:");
        for (Word word : entry.getValue()) {
            System.err.println("-> " + word.toString());
        }
    }
}
