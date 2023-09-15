package org.search_engine.logger;

import org.search_engine.utils.DateUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Type of logger specialized for file
 * */
public class FileLogger implements Logger{

    private final String filePath;

    public FileLogger(String filePath){
        this.filePath = filePath;
    }

    // responsible for info logs
    @Override
    public void log(String log) {

        String date = DateUtils.formatDate(new Date());

        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, true);
            writer.write("[");
            writer.write("INFO");
            writer.write("] ");
            writer.write(date);
            writer.write(": ");
            writer.write(log);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // responsible for error logs
    @Override
    public void error(String log) {

        String date = DateUtils.formatDate(new Date());

        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, true);
            writer.write("[");
            writer.write("ERR");
            writer.write("] ");
            writer.write(date);
            writer.write(": ");
            writer.write(log);
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
