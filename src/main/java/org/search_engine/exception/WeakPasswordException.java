package org.search_engine.exception;

/**
 * Responsible for weak password
 */
public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException(String message){
        super(message);
    }

}
