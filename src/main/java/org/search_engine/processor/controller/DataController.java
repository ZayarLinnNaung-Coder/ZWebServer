package org.search_engine.processor.controller;

import org.search_engine.enumeration.ResponseType;
import org.search_engine.model.User;
import org.search_engine.persistence.AuthContext;
import org.search_engine.service.AuthService;
import org.search_engine.service.UserService;
import org.search_engine.utils.FileUtils;
import org.search_engine.utils.GoogleAPIUtils;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataController extends BaseController{

    private final AuthService authService;
    private final GoogleAPIUtils googleAPIUtils;
    private final UserService userService;


    public DataController(){

        // Initialize dependencies
        authService = new AuthService();
        userService = new UserService();
        googleAPIUtils = new GoogleAPIUtils();
    }


    /**
     * Responsible for search data
     * */
    public void searchData(PrintWriter out, Map<String, String> paramMap){
        if(authService.isLogIn()){
            String searchQuery = paramMap.get("q");
            String response = googleAPIUtils.search(searchQuery);
            sendResponse(out, response, ResponseType.OK);
        }else{
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }

    /**
     * Responsible for save data
     * */
    public void saveData(PrintWriter out, Map<String, String> paramMap){
        if(authService.isLogIn()){
            String searchQuery = paramMap.get("q");
            AuthContext.getLoginUser().addCachedQuery(searchQuery);
            sendResponse(out, String.format("Successfully saved [ %s ] ", searchQuery), ResponseType.OK);
        }else {
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }

    /**
     * Responsible for load data
     * */
    public void loadData(PrintWriter out){
        if(authService.isLogIn()){
            String savedData = "";
            if(AuthContext.getLoginUser().getCachedQueries() != null){
                savedData = String.join("\n", AuthContext.getLoginUser().getCachedQueries());
            }
            sendResponse(out, savedData, ResponseType.OK);
        }else {
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }

    /**
     * Responsible for load friend data
     * */
    public void loadFriData(PrintWriter out, Map<String, String> paramMap){
        if(authService.isLogIn()){
            String savedData = "";
            if(AuthContext.getLoginUser().getCachedQueries() != null){
                String userId = paramMap.get("user");
                Optional<User> userOptional = userService.getUserById(userId);
                if(userOptional.isPresent()){
                    savedData = String.join("\n", userOptional.get().getCachedQueries());
                }
            }
            sendResponse(out, savedData, ResponseType.OK);
        }else {
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }


    /**
     * Responsible for load account
     * */
    public void loadAccount(PrintWriter out){
        if(authService.isLogIn()){
            String accounts = userService.getAllUsers().stream()
                    .map(User::getId).collect(Collectors.joining("\n"));
            sendResponse(out, accounts, ResponseType.OK);
        } else {
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }

    /**
     * Responsible for load logs
     * */
    public void loadLog(PrintWriter out){
        if(authService.isLogIn()){

            // system logs are stored in system_log.log
            String logs = FileUtils.read("data/system_log.log");
            sendResponse(out, logs, ResponseType.OK);
        } else {
            sendResponse(out, "Please login first", ResponseType.FORBIDDEN);
        }
    }
}
