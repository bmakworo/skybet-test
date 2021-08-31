package com.test.skybettest.service;

import com.test.skybettest.Exception.NoDataFoundException;
import com.test.skybettest.Exception.UserNotFoundException;
import com.test.skybettest.model.User;
import com.test.skybettest.provider.UserProvider;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserProvider userProviderMock;

    @InjectMocks
    private DefaultUserService defaultUserService;

    @Before
    public void before() {
        defaultUserService = new DefaultUserService(userProviderMock);
    }

    @Test
    void shouldReturnAllUsersForValidRequest() {
        List<User> userList = createUsers();

        when(userProviderMock.findAllUsers()).thenReturn(userList);
        defaultUserService.findAllUsers();
        assertEquals(2, userList.size());
        verify(userProviderMock, times(1)).findAllUsers();
    }

    @Test
    void shouldThrowNoDataFoundExceptionWhenUserListIsEmpty() {
        List<User> emptyUserList = new ArrayList<>();

        when(userProviderMock.findAllUsers()).thenReturn(emptyUserList);
        NoDataFoundException thrown = assertThrows(
                NoDataFoundException.class,
                () -> defaultUserService.findAllUsers(),
                "Message"
        );

        assertTrue(thrown.getMessage().contains("No data found"));
        assertThat(emptyUserList, is(empty()));
        verify(userProviderMock, times(1)).findAllUsers();
    }

    @Test
    void shouldFindUserByIdForValidRequest() {
        User user = new User(4,
                "userFirstName",
                "userLastName",
                "userEmailAddress",
                randomDateOfBirthGenerator());
        int userId = user.getId();
        when(userProviderMock.findUserById(userId)).thenReturn(user);
        defaultUserService.findUserById(userId);
        verify(userProviderMock, times(1)).findUserById(userId);
    }

    @Test
    void shouldReturn404ForMissingIdForFindUserByIdValidRequest() {
        User user = new User(4,
                "userFirstName",
                "userLastName",
                "userEmailAddress",
                randomDateOfBirthGenerator());

        UserNotFoundException thrown = assertThrows(
                UserNotFoundException.class,
                () -> defaultUserService.findUserById(6),
                "Message"
        );

        assertTrue(thrown.getMessage().contains("Could not find user 6"));
        verify(userProviderMock, times(1)).findUserById(6);
    }

    @Test
    void shouldCreateUserForValidRequest() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        User user = new User(16,
                "userFirstName",
                "userLastName",
                "userEmailAddress",
                randomDateOfBirthGenerator());

        defaultUserService.createUser(user);
        Mockito.verify(userProviderMock).createUser(captor.capture());

        User actual = captor.getValue();
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getDateOfBirth(), actual.getDateOfBirth());
        assertEquals(user.getEmailAddress(), actual.getEmailAddress());
    }

    @Test
    void shouldUpdateUserForValidRequest() {
        List<User> userList = createUsers();
        User userUpdate = new User(21,
                "userUpdateFirstName",
                "userUpdateLastName",
                "userUpdateEmailAddress",
                randomDateOfBirthGenerator());

        defaultUserService.updateUser(userUpdate, 0);

        assertNotEquals(userUpdate.getFirstName(), userList.get(0).getFirstName());
        assertNotEquals(userUpdate.getLastName(), userList.get(0).getLastName());
        assertNotEquals(userUpdate.getDateOfBirth(), userList.get(0).getDateOfBirth());
        assertNotEquals(userUpdate.getEmailAddress(), userList.get(0).getEmailAddress());
    }

    @Test
    void shouldDeleteUserByIdForValidRequest() {
        int userId = 42;
        defaultUserService.deleteUserById(userId);
        verify(userProviderMock, times(1)).deleteUserById(eq(userId));
    }

    private List<User> createUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1,
                "userOneFirstName",
                "userOneLastName",
                "userOneEmailAddress",
                randomDateOfBirthGenerator());

        User user2 = new User(2,
                "userTwoFirstName",
                "userTwoLastName",
                "userTwoEmailAddress",
                randomDateOfBirthGenerator());
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    private Date randomDateOfBirthGenerator() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        return Date.from(LocalDate.ofEpochDay(randomDay).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}