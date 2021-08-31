package com.test.skybettest.service;

import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final UserProvider userProvider;

    @Autowired
    DefaultUserService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public List<User> findAllUsers() {
        return userProvider.findAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userProvider.findUserById(id);
    }

    @Override
    public User createUser(User user) {
        return userProvider.createUser(user);
    }

    @Override
    public User updateUser(User user, int id) {
        return userProvider.updateUser(user,id);
    }

    @Override
    public void deleteUserById(int id) {
        userProvider.deleteUserById(id);
    }
}
