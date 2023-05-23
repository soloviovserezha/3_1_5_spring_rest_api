package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User getUserById(Long id);

    void deleteUserById(Long id);

    User changeUser(User user);

    void saveUser(User user);
}
