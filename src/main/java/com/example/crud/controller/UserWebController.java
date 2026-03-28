package com.example.crud.controller;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserWebController {

    @Autowired
    private UserRepository userRepository;

    // GET /users показать список
    @GetMapping("/users/{id}")
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    // GET /users/add форма добавления
    @GetMapping("/users/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    // POST /users/form сохранить
    @PostMapping("/users/form")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // GET /users/delete/{id} удалить
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
