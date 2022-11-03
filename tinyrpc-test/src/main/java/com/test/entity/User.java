package com.test.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer uid;
    private String username;

    public User(Integer uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                '}';
    }
}
