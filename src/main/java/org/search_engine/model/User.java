package org.search_engine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * USER ENTITY
 * */
public class User {
    private String id;
    private String password;
    private boolean isLeave;
    private List<String> cachedQueries;

    public User(String id, String password){
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean leave) {
        isLeave = leave;
    }

    public List<String> getCachedQueries() {
        if (cachedQueries == null){
            return new ArrayList<>();
        }
        return cachedQueries;
    }

    public void addCachedQuery(String query){
        if(cachedQueries == null){
           cachedQueries = new ArrayList<>();
        }
        cachedQueries.add(query);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", isLeave=" + isLeave +
                '}';
    }
}
