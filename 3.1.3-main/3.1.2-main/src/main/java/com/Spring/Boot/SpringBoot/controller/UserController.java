package com.Spring.Boot.SpringBoot.controller;

import com.Spring.Boot.SpringBoot.model.Role;
import com.Spring.Boot.SpringBoot.model.User;
import com.Spring.Boot.SpringBoot.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Spring.Boot.SpringBoot.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserServiceImp userServiceImp;

    @Autowired()
    public UserController(UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    // def
    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("userForBar", userServiceImp.findByUsername(principal.getName()));
        model.addAttribute("ROLE_ADMIN",userServiceImp.findRolesByName("ROLE_ADMIN"));
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user";
    }


    // def
    @GetMapping("/admin")
    public String users(Model model, Principal principal) {
        model.addAttribute("userForBar", userServiceImp.findByUsername(principal.getName()));
        model.addAttribute("user", userServiceImp.getAllUsers());
        model.addAttribute("roles", userServiceImp.listRoles());
        return "/index";
    }

    // def
    @GetMapping("/admin/new")
    public String addUser(Model model, Principal principal) {
        model.addAttribute("userForBar", userServiceImp.findByUsername(principal.getName()));
        model.addAttribute("roles", userServiceImp.listRoles());
        model.addAttribute("user", new User());
        return "new";
    }


    // def
    @PostMapping("/admin")
    public String add(@ModelAttribute("user") User user) {
        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }



    // def
    @GetMapping("admin/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("userOne", userServiceImp.getUserById(id));
        return "edit";
    }

    // def
    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("users")User user, @PathVariable("id") int id, @RequestParam(value = "role") String role) {
        user.setRoles(userServiceImp.findRolesByName(role));
        userServiceImp.updateUser(user, id);
        return "redirect:/admin";
    }

    // def
    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.deleteUserById(id);
        return "redirect:/admin";
    }
}
