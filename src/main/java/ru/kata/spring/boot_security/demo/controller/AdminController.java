package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

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
    public String showAdminGeneralPage(Principal principal, Model model) {
        User userP = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(userP.getId()));
        model.addAttribute("listOfUsers", userService.getAllUsers());
        model.addAttribute("role", userP.convertSetOfRoleToString(userService.showUser(userP.getId()).getRoles()));
        return "/viewsForAdmin/adminGeneralPage";
    }

   /* @GetMapping("/admin/personalPage")
    public String showUserForAdmin( Model model) {
        model.addAttribute("listOfUsers", userService.getAllUsers());
        return "/viewsForAdmin/adminPersonalPage";
    }*/


    @GetMapping("/admin/personalPage")
    public String showAdminPersonalPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("role", user.convertSetOfRoleToString(userService.showUser(user.getId()).getRoles()));
        return "/viewsForAdmin/adminPersonalPage";
    }

    @GetMapping("/admin/new")
    public String getViewForNewUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("user", new User());
        model.addAttribute("role", user.convertSetOfRoleToString(userService.showUser(user.getId()).getRoles()));
        return "/viewsForAdmin/new";
    }

    @PostMapping("/admin/newUser")
    public String addUser(@ModelAttribute("user") User user) {
        user.setRole((Set<Role>) userService.mapRolesToAuthorities(user.getRoles()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/users/login")
    public String getViewForUpdateUser() {
        return "/viewsForAdmin/login";
    }

    @PatchMapping("/admin/users/{id}/editUser")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Integer id) {
       // user.setRole((Set<Role>) userService.mapRolesToAuthorities(user.getRoles()));
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
