package com.test.skybettest.service;

import com.test.skybettest.Exception.NoDataFoundException;
import com.test.skybettest.Exception.UserNotFoundException;
import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        if (users.isEmpty()) {
            throw new NoDataFoundException();
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        List<User> allUsers = userProvider.findAllUsers();
        Optional<User> userFound = findUser(allUsers, id);

        if (userFound.isPresent()) {
            User user = userProvider.findUserById(allUsers.indexOf(userFound.get()));
            return user;
        } else {
            throw new UserNotFoundException(id);
        }
    }

    private final Optional<User> findUser(Collection<User> userList, int id) {
        return userList.stream().filter(c -> c.getId() == id).findAny();
    }

    @Override
    public User createUser(User user) {
        return userProvider.createUser(user);
    }

    @Override
    public User updateUser(User user, int id) {
        List<User> allUsers = userProvider.findAllUsers();
        Optional<User> userFound = findUser(allUsers, id);

        if (userFound.isPresent()) {
            return userProvider.updateUser(userFound.get(), user);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public void deleteUserById(int id) {
        List<User> allUsers = userProvider.findAllUsers();
        Optional<User> userFound = findUser(allUsers, id);

        if (userFound.isPresent()) {
            userProvider.deleteUserById(allUsers.indexOf(userFound.get()));
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
