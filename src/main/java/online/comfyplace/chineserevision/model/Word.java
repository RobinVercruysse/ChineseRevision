package online.comfyplace.chineserevision.model;

public record Word(String traditionalCharacter, String pinyin, String english) {

    @Override
    public String toString() {
        return String.format("English: %s, Pinyin: %s, Simplified: %s", english(), pinyin(), traditionalCharacter());
    }
}
