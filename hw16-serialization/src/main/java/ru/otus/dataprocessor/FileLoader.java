package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class FileLoader implements Loader {

    private final Gson gson;
    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
        this.gson = new Gson();
    }

    @Override
    public List<Measurement> load() {
        try(Reader reader = new InputStreamReader(new FileInputStream(fileName))) {
            Measurement[] measurements = gson.fromJson(reader, Measurement[].class);
            return Arrays.asList(measurements);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
