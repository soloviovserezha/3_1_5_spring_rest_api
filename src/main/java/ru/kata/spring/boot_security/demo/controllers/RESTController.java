package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NoUserException;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class RESTController {

    private Logger logger = Logger.getLogger(RESTController.class.getName());

    private UserServiceImpl userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        Set<Role> roleList = roleService.getRoles();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new NoUserException("User with ID = " + id + " not found");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
        logger.info("call ddNewUser()");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/users/{id}")
    public ResponseEntity<User> changeUser(@RequestBody User user, @PathVariable(name = "id") Long id) {
        userService.changeUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoUserException("User with ID = " + id + " not found");
        }
        userService.deleteUserById(id);
    }
}
