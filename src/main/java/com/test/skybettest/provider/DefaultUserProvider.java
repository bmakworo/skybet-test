package com.test.skybettest.provider;

import com.test.skybettest.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultUserProvider implements UserProvider{

    private List<User> userList;
}
