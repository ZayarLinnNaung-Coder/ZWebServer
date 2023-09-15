package org.search_engine.processor.controller;

import org.search_engine.enumeration.ResponseType;
import org.search_engine.logger.ConsoleLogger;
import org.search_engine.logger.FileLogger;
import org.search_engine.logger.Logger;

import java.io.PrintWriter;

// Parent of all other controllers
public class BaseController {

    // File logger and console logger can accessible from child
    protected static final Logger errorLogLogger = new FileLogger("data/system_error_log.log");
    protected static final Logger consoleLogger = new ConsoleLogger();

    /**
     * sending appropriate responses
     * */
    static void sendResponse(PrintWriter out, String content, ResponseType responseType){
        out.print("HTTP/1.1 ");
        out.print(responseType.getStatusCode());
        out.print(" ");
        out.println(responseType.name());
        out.println("Content-Type: application/json");
        out.println("Access-Control-Allow-Origin: *");
        out.println("Content-Length: " + content.length());
        out.println();

        if(!ResponseType.OK.equals(responseType)){
            errorLogLogger.error(content);
            consoleLogger.error(content);
        }else{
            consoleLogger.log(content);
        }

        out.println(content);
    }
}
