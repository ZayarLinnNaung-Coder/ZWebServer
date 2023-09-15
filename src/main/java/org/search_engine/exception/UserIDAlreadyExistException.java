package org.search_engine.exception;

/**
 * Responsible for duplicate userId exceptions
 */
public class UserIDAlreadyExistException extends RuntimeException {

    public UserIDAlreadyExistException(String message){
        super(message);
    }

}
