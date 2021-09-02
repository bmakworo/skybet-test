package com.test.skybettest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.skybettest.Exception.NoDataFoundException;
import com.test.skybettest.model.User;
import com.test.skybettest.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRetrieveAllUsersForValidRequest() throws Exception {
        Mockito.when(mockUserService.findAllUsers()).thenReturn(
                Collections.singletonList(new User(42, "firstDummyName", "lasttDummyName", "dummy@email.com", new SimpleDateFormat("dd/MM/yyyy").parse("15/04/1978"))));

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionNoUsersExist() throws Exception {
        Mockito.when(mockUserService.findAllUsers()).thenThrow(NoDataFoundException.class);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void ShouldRetrieveUserByIdForValidRequest() throws Exception {
        Mockito.when(mockUserService.findUserById(anyInt())).thenReturn(
                new User(42, "firstDummyName", "lasttDummyName", "dummy@email.com", new SimpleDateFormat("dd/MM/yyyy").parse("15/04/1978")));

        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(42)))
                .andExpect(jsonPath("$.firstName", is("firstDummyName")))
                .andExpect(jsonPath("$.lastName", is("lasttDummyName")))
                .andExpect(jsonPath("$.emailAddress", is("dummy@email.com")));
    }

    @Test
    void shouldCreateUserForValidRequest() throws Exception {
        User user = new User(2, "firstName", "lastName", "email@mail.com", new SimpleDateFormat("dd/MM/yyyy").parse("15/04/1978"));
        Mockito.when(mockUserService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.emailAddress", is("email@mail.com")));
    }

    @Test
    void shouldUpdateUserForValidRequest() throws Exception {
        User user = new User(2, "firstName2", "lastName2", "email2@email.com", new SimpleDateFormat("dd/MM/yyyy").parse("15/04/1978"));
        Mockito.when(mockUserService.updateUser(any(User.class), anyInt())).thenReturn(user);

        mockMvc.perform(put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("firstName2")))
                .andExpect(jsonPath("$.lastName", is("lastName2")))
                .andExpect(jsonPath("$.emailAddress", is("email2@email.com")));
    }

    @Test
    void shouldDeleteUserByIdForValidRequest() throws Exception {
        mockMvc.perform(delete("/user/{id}", 21)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}