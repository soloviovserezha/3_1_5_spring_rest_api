package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.security.Principal;


@Controller
@RequestMapping
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAuthUser(@CurrentSecurityContext(expression = "authentication") Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/users")
    public String getAllUsers(Model model, Principal principal) {
        User userPrincipal = userService.findByUsername(principal.getName());
        model.addAttribute("userPrincipal", userPrincipal);
        model.addAttribute("users", userService.getUserList());
        return "user-page";
    }
}
