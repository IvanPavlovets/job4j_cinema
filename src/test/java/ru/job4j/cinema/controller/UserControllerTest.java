package ru.job4j.cinema.controller;

import org.junit.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    @Test
    public void whenRegistration() {
        User user = new User(1, "name", "email", "phone");
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        userController.registration(user);
        verify(userService).add(user);
    }

}
