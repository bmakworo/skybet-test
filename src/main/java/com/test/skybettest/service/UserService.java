package com.test.skybettest.service;

import com.test.skybettest.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(int id);

    User createUser(User user);

    User updateUser(User user, int id);

    void deleteUserById(int id);
}
