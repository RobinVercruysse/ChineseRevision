package online.comfyplace.chineserevision.repository;

import online.comfyplace.chineserevision.model.Word;

import java.util.List;
import java.util.Random;

public class WordsRepository {
    private final List<Word> words;
    private final Random random = new Random();

    public WordsRepository(List<Word> words) {
        this.words = words;
    }

    public Word getRandomWord() {
        final int index = random.nextInt(words.size());

        return words.get(index);
    }

    public List<Word> allWords() {
        return words;
    }
}
