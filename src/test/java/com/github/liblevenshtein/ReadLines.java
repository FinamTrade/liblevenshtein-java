package com.github.liblevenshtein;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadLines {

    public static List<String> fromURL(URL url) {
        try {
            Stream<String> stream = Files.lines(Paths.get(url.toURI()));
            return stream.collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
