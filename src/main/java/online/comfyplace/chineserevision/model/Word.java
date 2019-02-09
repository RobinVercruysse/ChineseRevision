package online.comfyplace.chineserevision.model;

public class Word {
    private final String english;
    private final String pinyin;
    private final String simplified;

    public Word(String english, String pinyin, String simplified) {
        this.english = english;
        this.pinyin = pinyin;
        this.simplified = simplified;
    }

    public String getEnglish() {
        return english;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getSimplified() {
        return simplified;
    }

    @Override
    public String toString() {
        return String.format("English: %s, Pinyin: %s, Simplified: %s", getEnglish(), getPinyin(), getSimplified());
    }
}
