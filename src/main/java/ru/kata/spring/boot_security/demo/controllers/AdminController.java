package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users/testJS")
    public String getAdminTestJS(Model model, Principal principal) {
        return "testJS";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal) {
        User userPrincipal = userService.findByUsername(principal.getName());
        model.addAttribute("userPrincipal", userPrincipal);
        return "allUsers";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin-page";
    }

    @PostMapping("/users/add")
    public String saveUserPost(User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin-page";
    }

    @PatchMapping(value = "/users/{id}/edit")
    public String changeUser(User user) {
        userService.changeUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}/remove")
    public String deleteUserById(@PathVariable(value = "id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
