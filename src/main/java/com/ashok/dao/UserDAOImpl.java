package com.ashok.dao;

import com.ashok.entity.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDAOImpl implements UserDAO {


    private final MongoDatabase database;
    private MongoCollection<User> collection;


    public UserDAOImpl(MongoDatabase database, MongoCollection<User> collection) {
        this.database = database;
        this.collection = collection;
    }

    @Override
    public void createUser(User user) {
        collection.insertOne(user);
    }

    @Override
    public void updateUser(User user) {
        collection.updateOne(Filters.eq("userId", user.getUserId()),
                Updates.set("name", user.getName()));
    }

    @Override
    public void deleteUser(String userId) {
        collection.deleteOne(Filters.eq("userId", userId));
    }

    @Override
    public User getUser(String userId) {
        Bson filter = Filters.eq("userId", userId);
        FindIterable<User> users = collection.find(filter);
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        FindIterable<User> iterableUsers = collection.find();
        Iterator<User> it = iterableUsers.iterator();
        while (it.hasNext()) {
            users.add(it.next());
        }
        return users;
    }
}
