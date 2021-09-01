package com.test.skybettest;

import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserProvider userProvider;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(13, "Paul", "Smith", "paul.smith@gmail.com", new SimpleDateFormat("dd/MM/yyyy").parse("15/04/1978"));
        userProvider.createUser(u1);

        User u2 = new User(18, "Robert", "Black", "rb34@gmail.com", new SimpleDateFormat("dd/MM/yyyy").parse("05/09/1989"));
        userProvider.createUser(u2);

        User u3 = new User(100, "John", "Doe", "jdoe@gmail.com", new SimpleDateFormat("dd/MM/yyyy").parse("17/06/1965"));
        userProvider.createUser(u3);
    }
}
