package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.io.DictionaryReader;
import online.comfyplace.chineserevision.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChineseRevision {
    public static void main(String[] args) throws IOException {
        final List<Word> dictionary;
        try (final DictionaryReader reader = new DictionaryReader("words.properties")) {
            dictionary = reader.readAll();
        }
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
