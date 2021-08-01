package com.ashok.entity;

import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    private String userId;
    private String name;

    public User() {
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
