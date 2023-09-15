package org.search_engine.processor.controller;

import org.search_engine.enumeration.ResponseType;
import org.search_engine.exception.UserIDAlreadyExistException;
import org.search_engine.exception.WeakPasswordException;
import org.search_engine.model.User;
import org.search_engine.persistence.AuthContext;
import org.search_engine.service.UserService;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

public class UserController extends BaseController{

    private static UserService userService;

    public UserController(){

        // Initialize dependencies
        userService = new UserService();
    }

    /**
    * Responsible for joinUser api
    * */
    public void joinUser(PrintWriter out, Map<String, String> paramMap){
        try{
            User requestUser = getUserFromParam(paramMap);

            // validate request user is valid
            boolean isValidUser = userService.isValid(requestUser);
            if(isValidUser){
                userService.saveUser(requestUser);
                sendResponse(out, "Successfully joined", ResponseType.OK);
            }
        }catch (UserIDAlreadyExistException e){
            sendResponse(out, e.getLocalizedMessage(), ResponseType.BAD_REQUEST);
        }catch (WeakPasswordException e){
            sendResponse(out, e.getLocalizedMessage(), ResponseType.BAD_REQUEST);
        }
    }

    /**
     * Responsible for login api
     * */
    public void loginUser(PrintWriter out, Map<String, String> paramMap){
        User requestUser = getUserFromParam(paramMap);

        Optional<User> userOptional = userService.isUserRegistered(requestUser);

        // user must be already registered and it's not in member withdraw
        if (userOptional.isPresent() && !userOptional.get().isLeave()){
            AuthContext.setLoginUser(userOptional.get());
            sendResponse(out, "Successfully login", ResponseType.OK);
        } else {
            sendResponse(out, requestUser.getId() + " has not joined yet.", ResponseType.BAD_REQUEST);
        }
    }

    /**
     * Responsible for logout api
     * */
    public void logoutUser(PrintWriter out, Map<String, String> paramMap){
        User requestUser = getUserFromParam(paramMap);

        // check if user's already logged in
        if(AuthContext.getLoginUser() != null){
            if(AuthContext.getLoginUser().getId().equals(requestUser.getId())){
                AuthContext.setLoginUser(null);
                sendResponse(out, "Successfully logout", ResponseType.OK);
            } else {
                sendResponse(out, "No enough authorization", ResponseType.FORBIDDEN);
            }
        } else {
            sendResponse(out, "Please login first", ResponseType.BAD_REQUEST);
        }
    }

    /**
     * Responsible for leaveUser api
     * personally thought this as the same principle such as Facebook Deactivate
     * */
    public void leaveUser(PrintWriter out, Map<String, String> paramMap){
        User requestUser = getUserFromParam(paramMap);

        // check if user's already registered
        Optional<User> optionalUser = userService.isUserRegistered(requestUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setLeave(true);
            userService.updateUser(user);
            sendResponse(out, "Successfully left", ResponseType.OK);
        } else {
            sendResponse(out, requestUser.getId() + " has not joined yet.", ResponseType.BAD_REQUEST);
        }
    }

    /**
     * Responsible for recover user api
     * */
    public void recoverUser(PrintWriter out, Map<String, String> paramMap){
        User requestUser = getUserFromParam(paramMap);

        // check if user's already registered
        Optional<User> userOptional = userService.isUserRegistered(requestUser);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setLeave(false);
            userService.updateUser(user);
            sendResponse(out, "Successfully recovered", ResponseType.OK);
        } else {
            sendResponse(out, requestUser.getId() + " has not joined yet.", ResponseType.BAD_REQUEST);
        }
    }

    private static User getUserFromParam(Map<String, String> paramMap){
        return new User(paramMap.get("id"), paramMap.get("passwd"));
    }
}
