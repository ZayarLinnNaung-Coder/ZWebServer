package org.search_engine.service;

import org.search_engine.exception.UserIDAlreadyExistException;
import org.search_engine.exception.WeakPasswordException;
import org.search_engine.model.User;
import org.search_engine.persistence.UserDB;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserService {

    public void saveUser(User user){
        UserDB.getStoredUsers().add(user);
    }

    public void saveAllUsers(List<User> userList){
        userList.forEach(this::saveUser);
    }

    public Optional<User> isUserRegistered(User requestUser) {
        return getAllUsers()
                .stream().filter(u -> u.getId().equals(requestUser.getId()) && u.getPassword().equals(requestUser.getPassword())).findFirst();
    }

    public boolean isValid(User user){

        // check for duplicate user first
        if(!isDuplicateUserId(user.getId())){

            // if not found, check for strong password
            if(isStrongUserPassword(user.getPassword())){
                return true;
            } else {
                throw new WeakPasswordException("Password must be at least 4 characters long. Must start with alphabet " +
                        "and only allow @ and % for special symbols");
            }
        } else {
            throw new UserIDAlreadyExistException(user.getId() + " already exists.");
        }
    }

    public List<User> getAllUsers(){
        return UserDB.getStoredUsers();
    }

    public void updateUser(User user){

        // find the index of update user from userDB and update
        int updateUserIndex = getAllUsers().indexOf(user);
        getAllUsers().set(updateUserIndex, user);
    }

    public Optional<User> getUserById(String userId){
        return getAllUsers().stream().filter(u -> userId.equals(u.getId())).findFirst();
    }

    private boolean isDuplicateUserId(String userId){
        return getAllUsers()
                .stream().anyMatch(u -> u.getId().equals(userId));
    }

    private boolean isStrongUserPassword(String userPassword){
        return Pattern.matches("^[a-zA-Z][a-zA-Z@%#]{3,}$", userPassword);
    }
}
