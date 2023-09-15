package org.search_engine.persistence;

import org.search_engine.model.User;

/**
 * STORAGE FOR LOG_IN USERS
 * */
public class AuthContext {
    private static User loginUser;

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        AuthContext.loginUser = loginUser;
    }
}
