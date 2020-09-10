package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.io.DictionaryReader;
import online.comfyplace.chineserevision.model.Word;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ChineseRevision {
    public static void main(String[] args) throws IOException {
        System.out.println("Loading dictionary...");
        final List<Word> dictionary;
        try (final DictionaryReader reader = new DictionaryReader("words.csv")) {
            dictionary = reader.readAll();
        }
        System.out.println(String.format("Finished loading dictionary of %d words", dictionary.size()));
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. English -> Pinyin");
        System.out.println("2. Pinyin -> English");
        System.out.println("3. Pinyin -> Simplified");
        System.out.println("4. Simplified -> Pinyin");
        final int revisionMode = scanner.nextInt();
        final Revision revision = new Revision(dictionary);
        while (revision.hasNext()) {
            final Word currentWord = revision.next();
            switch (revisionMode) {
                case 1:
                    System.out.println(String.format("%s -> %s", currentWord.getEnglish(), currentWord.getPinyin()));
                    break;
                case 2:
                    System.out.println(String.format("%s -> %s", currentWord.getPinyin(), currentWord.getEnglish()));
                    break;
                case 3:
                    System.out.println(String.format("%s -> %s", currentWord.getPinyin(), currentWord.getSimplified()));
                    break;
                case 4:
                    System.out.println(String.format("%s -> %s", currentWord.getSimplified(), currentWord.getPinyin()));
                    break;
            }
        }
    }
}
