package com.example.demo.controller;



import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/test")
    @ResponseBody
    public List<Role> testRoles() {
        return roleService.findAll();
    }
    @GetMapping("/list")
    public String getRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "list";  // Шаблон для списка ролей
    }

    @GetMapping("/create")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "create";  // Шаблон для создания роли
    }
    @Transactional
    @PostMapping
    public String createRole(@ModelAttribute Role role) {
        roleService.save(role);
        return "redirect:/roles/list";  // Перенаправление на список ролей
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return "edit"; // Шаблон для редактирования роли
    }

    @PostMapping("/update/{id}")
    public String updateRole(@PathVariable Long id, @ModelAttribute Role role) {
        role.setId(id);
        roleService.save(role);
        return "redirect:/roles/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return "redirect:/roles/list"; // Переход после удаления
    }
}

