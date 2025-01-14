package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/create1")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "create1";  // Шаблон для создания пользователя
    }
    @Transactional
    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        // Получаем роль по ID, который был передан из формы
        Role role = roleService.findById(user.getRole().getId());
        user.setRole(role);  // Устанавливаем роль в пользователя

        userService.save(user); // Сохраняем пользователя в базу данных
        return "redirect:/users/list1"; // Перенаправление на список пользователей
    }



    @GetMapping("/list1")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllWithRoles());
        return "list1";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "edit1";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users/list1"; // Переход после удаления
    }
}
