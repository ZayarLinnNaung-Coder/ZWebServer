package org.search_engine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * UTILITY FOR FILEs
 * */
public class FileUtils {

    // Default read files
    public static String read(String filePath){

        StringBuilder data = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            while (line != null) {
                data.append(line);
                data.append("\n");

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toString();
    }
}
