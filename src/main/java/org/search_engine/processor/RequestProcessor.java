package org.search_engine.processor;

import static org.search_engine.constant.RequestPath.*;

import org.search_engine.logger.ConsoleLogger;
import org.search_engine.logger.FileLogger;
import org.search_engine.logger.Logger;
import org.search_engine.processor.controller.DataController;
import org.search_engine.processor.controller.UserController;
import org.search_engine.utils.ParamUtils;

import java.io.PrintWriter;
import java.util.Map;

public class RequestProcessor {

    private static final UserController userController = new UserController();
    private static final DataController dataController = new DataController();

    // change any logger what you need
    private static final Logger fileLogger = new FileLogger("data/system_log.log");
    private static final Logger consoleLogger = new ConsoleLogger();

    public static void process(PrintWriter out, String request){

        // save every request in system_log.log
        consoleLogger.log(request);
        fileLogger.log(request);

        String path = ParamUtils.getPath(request);
        Map<String, String> paramMap = ParamUtils.getAllParam(request);

        switch (path){
            case USER_JOIN:
                userController.joinUser(out, paramMap);
                break;
            case USER_LOGIN:
                userController.loginUser(out, paramMap);
                break;
            case USER_LOGOUT:
                userController.logoutUser(out, paramMap);
                break;
            case USER_LEAVE:
                userController.leaveUser(out, paramMap);
                break;
            case USER_RECOVER:
                userController.recoverUser(out, paramMap);
                break;
            case DATA_SEARCH:
                dataController.searchData(out, paramMap);
                break;
            case DATA_SAVE_DATA:
                dataController.saveData(out, paramMap);
                break;
            case DATA_LOAD_DATA:
                dataController.loadData(out);
                break;
            case DATA_LOAD_FRI:
                dataController.loadFriData(out, paramMap);
                break;
            case DATA_LOAD_ACC:
                dataController.loadAccount(out);
                break;
            case DATA_LOAD_LOG:
                dataController.loadLog(out);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + path);
        }

    }

}
