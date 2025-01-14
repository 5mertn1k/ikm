package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService; // Добавлено

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService; // Инициализация
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllWithRoles() {
        return userRepository.findAll(); // Роли подтягиваются автоматически
    }

    @Transactional
    public void save(User user) {
        // Проверяем, что роль корректно присвоена
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new IllegalArgumentException("User must have a valid role.");
        }
        userRepository.save(user); // Сохраняем пользователя в репозитории
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
