package online.comfyplace.chineserevision.io;

import online.comfyplace.chineserevision.model.Word;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryReader implements Closeable {
    private final BufferedReader reader;

    public DictionaryReader(final String fileName) throws IOException {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException(String.format("Unable to read dictionary from file %s", fileName));
        }
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public List<Word> readAll() {
        return reader.lines().map(this::parseLineToWord).collect(Collectors.toList());
    }

    private Word parseLineToWord(final String line) {
        final String[] keyValue = line.split("=");
        final String[] valueParts = keyValue[1].split(",");
        return new Word(keyValue[0], valueParts[0], valueParts[1]);
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            reader.close();
        }
    }
}
