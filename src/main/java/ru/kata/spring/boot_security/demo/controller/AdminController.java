package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAdminGeneralPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("listOfUsers", userService.getAllUsers());
        model.addAttribute("personalRole", user.convertSetOfRoleToString(userService.showUser(user.getId()).getRoles()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/viewsForAdmin/adminGeneralPage";
    }


    @GetMapping("/personalPage")
    public String showAdminPersonalPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("role", user.convertSetOfRoleToString(userService.showUser(user.getId()).getRoles()));
        return "/viewsForAdmin/adminPersonalPage";
    }

    @GetMapping("/new")
    public String getViewForNewUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.showUser(user.getId()));
        model.addAttribute("user", new User());
        model.addAttribute("personalRole", user.convertSetOfRoleToString(userService.showUser(user.getId()).getRoles()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/viewsForAdmin/new";
    }

    @PostMapping("/newUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/users/{id}/editUser")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
