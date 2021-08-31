package com.test.skybettest.service;

import com.test.skybettest.Exception.NoDataFoundException;
import com.test.skybettest.Exception.UserNotFoundException;
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
        List<User> users = userProvider.findAllUsers();
        if(users.isEmpty()) {
            throw new NoDataFoundException();
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        User user;
        try {
            user = userProvider.findUserById(id);
        } catch (UserNotFoundException unfe) {
            throw new UserNotFoundException(id);
        }
        return user;
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
        try {
            userProvider.deleteUserById(id);
        } catch (UserNotFoundException unfe) {
            throw new UserNotFoundException(id);
        }
    }
}
