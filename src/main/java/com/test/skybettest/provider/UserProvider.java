package com.test.skybettest.provider;

import com.test.skybettest.model.User;

import java.util.List;

public interface UserProvider {

    List<User> findAllUsers();

    User findUserById(int id) ;

    User createUser(User user);

    User updateUser(User oldUser, User newUser);

    void deleteUserById(int id);
}
