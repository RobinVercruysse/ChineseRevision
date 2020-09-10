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
        final String[] parts = line.split(",");
        return new Word(parts[0], parts[1], parts[2]);
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            reader.close();
        }
    }
}
