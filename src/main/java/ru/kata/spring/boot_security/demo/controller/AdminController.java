package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAdmin(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("listOfUsers", userService.getAllUsers());
        return "/viewsForAdmin/admin";
    }

 /*   @GetMapping("/admin/users/{id}")
    public String showUserForAdmin(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/viewsForAdmin/showUser";
    }*/


    @GetMapping("/admin/personalPage")
    public String getAllUsers(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        return "/viewsForAdmin/adminPersonalPage";
    }

    @GetMapping("/admin/users/new")
    public String getViewForNewUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("user", new User());
        return "/viewsForAdmin/new";
    }

    @PostMapping("/admin/users/newUser")
    public String addUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/admin/users/{id}/edit")
    public String getViewForUpdateUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.showUser(id));
        return "/viewsForAdmin/edit";
    }

    @PatchMapping("/admin/users/{id}/editUser")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
