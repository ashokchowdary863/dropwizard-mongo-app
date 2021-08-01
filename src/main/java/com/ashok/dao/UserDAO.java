package com.ashok.dao;

import com.ashok.entity.User;

import java.util.List;

public interface UserDAO {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User getUser(String userId);

    List<User> getUsers();
}
