package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersDetailsService;

import java.security.Principal;

@Controller
public class UsersController {

    public final UserServiceImpl userService;

    public final UsersDetailsService usersDetailsService;

    @Autowired
    public UsersController(UserServiceImpl service, UsersDetailsService usersDetailsService) {
        this.userService = service;
        this.usersDetailsService = usersDetailsService;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "/index";
    }


    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        User user = usersDetailsService.findByUsername(principal.getName());
        model.addAttribute("user", userService.showUser(user.getId()));
        return "/user";
    }

    @GetMapping("/admin/users/{id}")
    public String showUserForAdmin(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/viewsForAdmin/showUser";
    }

    @GetMapping("/admin")
    public String showAdmin(Principal principal, Model model) {
        User user = usersDetailsService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        return "/viewsForAdmin/admin";
    }
//   Все пользователи
    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("listOfUsers", userService.getAllUsers());
        return "/viewsForAdmin/allUsers";
    }

//   Страница для создания нового пользователя
    @GetMapping("/admin/users/new")
    public String getViewForNewUser(Model model) {
        model.addAttribute("user", new User());
        return "/viewsForAdmin/new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }
//  Страница для отображения формы для изменения пользователя
    @GetMapping("/admin/users/edit")
    public String getViewForUpdateUser(Model model, Principal principal) {
        User user = usersDetailsService.findByUsername(principal.getName());
        model.addAttribute("user", userService.showUser(user.getId()));
        return "/viewsForAdmin/edit";
    }

    @PatchMapping("/admin/users/edit")
    public String updateUser(@ModelAttribute("user") User user, Principal principal) {
        User principalUser = usersDetailsService.findByUsername(principal.getName());
        userService.updateUser(principalUser.getId(), user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
