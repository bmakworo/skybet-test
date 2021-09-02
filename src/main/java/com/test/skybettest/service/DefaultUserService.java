package com.test.skybettest.service;

import com.test.skybettest.Exception.NoDataFoundException;
import com.test.skybettest.Exception.UserNotFoundException;
import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserService.class);
    private final UserProvider userProvider;

    @Autowired
    DefaultUserService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userProvider.findAllUsers();
        if (users.isEmpty()) {
            log.error("NoDataFoundException thrown");
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
            log.error("UserNotFoundException thrown for id: "+id);
            throw new UserNotFoundException(id);
        }
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
            log.error("UserNotFoundException exception for id: "+id);
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
            log.error("UserNotFoundException thrown for id: "+id);
            throw new UserNotFoundException(id);
        }
    }

    private final Optional<User> findUser(Collection<User> userList, int id) {
        return userList.stream().filter(c -> c.getId() == id).findAny();
    }
}
