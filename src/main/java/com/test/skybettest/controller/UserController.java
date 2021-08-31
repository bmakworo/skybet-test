package com.test.skybettest.controller;

import com.test.skybettest.model.User;
import com.test.skybettest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<User> retrieveAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    User retrieveUserById(@PathVariable int id){
        return userService.findUserById(id);
    }

    @PostMapping("/user")
    User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User user, @PathVariable int id){
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
