package com.github.liblevenshtein;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadLines {

    public static List<String> fromResources(String resourceName) {
        List<String> result = new ArrayList<String>();
        BufferedReader buffReader = null;
        try {
            ClassLoader classLoader = ReadLines.class.getClassLoader();
            buffReader = new BufferedReader(new FileReader(new File(classLoader.getResource(resourceName).getFile())));
            String line = buffReader.readLine();
            while (line != null) {
                result.add(line);
                line = buffReader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                buffReader.close();
            } catch (IOException ioe1) {
                //Leave It
            }
        }
        return result;
    }
}
