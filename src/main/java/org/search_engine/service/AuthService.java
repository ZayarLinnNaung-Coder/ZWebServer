package org.search_engine.service;

import org.search_engine.persistence.AuthContext;

public class AuthService {
    public boolean isLogIn(){
        return AuthContext.getLoginUser() != null;
    }
}
