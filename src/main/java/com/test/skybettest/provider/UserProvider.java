package com.test.skybettest.provider;

import com.test.skybettest.model.User;

import java.util.List;

public interface UserProvider {

    List<User> findAllUsers();

    User findUserById(int id) ;

    User createUser(User user);

    User updateUser(User user, int id);

    void deleteUserById(int id);
}
