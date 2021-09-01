package com.test.skybettest.provider;

import com.test.skybettest.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultUserProvider implements UserProvider{

    private List<User> userList;

    DefaultUserProvider(){
        userList = new ArrayList<>();
    }

    public List<User> findAllUsers() {
        return userList;
    }

    public User findUserById(int id) {
        return userList.get(id);
    }

    public User createUser(User user) {
        userList.add(user);
        return user;
    }

    public User updateUser(User user, User newUser) {
        user.setId(newUser.getId());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmailAddress(newUser.getEmailAddress());
        user.setDateOfBirth(newUser.getDateOfBirth());

        return user;
    }

    public void deleteUserById(int id) {
        userList.remove(id);
    }
}
