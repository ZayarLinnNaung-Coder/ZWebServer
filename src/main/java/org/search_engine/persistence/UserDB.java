package org.search_engine.persistence;

import org.search_engine.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * STORAGE FOR USERS
 * USE AS A IN-MEMORY DB FOR ALL USERS
 * */
public class UserDB {
    private static List<User> storedUsers;

    public static List<User> getStoredUsers(){
        // Encapsulate storedUsers from direct access known as `singleton pattern`
        if(storedUsers == null){
            storedUsers = new ArrayList<>();
        }
        return storedUsers;
    }
}
