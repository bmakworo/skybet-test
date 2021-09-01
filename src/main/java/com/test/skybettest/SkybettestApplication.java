package com.test.skybettest;

import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import com.test.skybettest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class SkybettestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkybettestApplication.class, args);
    }
}
