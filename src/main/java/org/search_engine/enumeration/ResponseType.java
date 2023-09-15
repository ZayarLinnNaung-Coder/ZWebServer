package org.search_engine.enumeration;

/**
 * ENUM OF HTTP RESPONSE TYPES
 * */
public enum ResponseType {

    OK(200),
    BAD_REQUEST(400),
    FORBIDDEN(403);

    private int statusCode;

    ResponseType(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
