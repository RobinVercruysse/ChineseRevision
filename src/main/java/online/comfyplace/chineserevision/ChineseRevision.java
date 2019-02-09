package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ChineseRevision {
    public static void main(String[] args) {
        try (final InputStream wordsInputStream = ChineseRevision.class.getClassLoader().getResourceAsStream("words.properties");
             final BufferedReader reader = new BufferedReader(new InputStreamReader(wordsInputStream))) {
            final List<Word> dictionary = reader.lines().map(ChineseRevision::parseLineToWord).collect(Collectors.toList());
            dictionary.forEach(word -> {
                System.out.println("*******************");
                System.out.println("English: " + word.getEnglish());
                System.out.println("Pinyin: " + word.getPinyin());
                System.out.println("Simplified: " + word.getSimplified());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Word parseLineToWord(final String line) {
        final String[] keyValue = line.split("=");
        final String[] valueParts = keyValue[1].split(",");
        return new Word(keyValue[0], valueParts[0], valueParts[1]);
    }
}
