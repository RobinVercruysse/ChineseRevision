package online.comfyplace.chineserevision;

import online.comfyplace.chineserevision.model.Word;

import java.util.*;

public class Revision {
    private final Map<Boolean, List<Word>> dictionaryByRevisionStatus = new HashMap<>();

    public Revision(List<Word> dictionary) {
        dictionaryByRevisionStatus.put(true, new ArrayList<>());
        dictionaryByRevisionStatus.put(false, new ArrayList<>(dictionary));
    }

    public boolean hasNext() {
        return !dictionaryByRevisionStatus.get(false).isEmpty();
    }

    public Word next() {
        final int unrevisedLength = dictionaryByRevisionStatus.get(false).size();
        if (unrevisedLength <= 0) {
            return null;
        }
        final int nextIndex = new Random().nextInt(unrevisedLength);
        final Word nextWord = dictionaryByRevisionStatus.get(false).remove(nextIndex);
        dictionaryByRevisionStatus.get(true).add(nextWord);
        return nextWord;
    }
}
