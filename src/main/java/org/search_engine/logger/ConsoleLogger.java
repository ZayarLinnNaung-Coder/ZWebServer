package org.search_engine.logger;

import org.search_engine.utils.DateUtils;

import java.util.Date;

/**
 * Type of logger specialized for console
 * */
public class ConsoleLogger implements Logger{


    // responsible for info logs
    @Override
    public void log(String log) {
        String date = DateUtils.formatDate(new Date());
        System.out.println(String.format("[%s] %s", date, log));
    }

    // responsible for error logs
    @Override
    public void error(String log) {
        String date = DateUtils.formatDate(new Date());
        System.err.println(String.format("[%s] %s", date, log));
    }
}
