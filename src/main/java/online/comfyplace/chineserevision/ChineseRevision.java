package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.io.DictionaryReader;
import online.comfyplace.chineserevision.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ChineseRevision {
    public static void main(String[] args) throws IOException {
        final List<Word> dictionary;
        try (final DictionaryReader reader = new DictionaryReader("words.properties")) {
            dictionary = reader.read();
        }
        dictionary.forEach(System.out::println);
    }
}
