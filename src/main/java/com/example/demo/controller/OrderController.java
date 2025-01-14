package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/list2")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "list2"; // Шаблон для списка заказов
    }

    @GetMapping("/create2")
    public String createForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.findAll());
        return "create2"; // Шаблон для создания заказа
    }
    @Transactional
    @PostMapping
    public String saveOrder(@ModelAttribute Order order) {
        orderService.save(order);
        return "redirect:/orders/list2"; // Переход после сохранения
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("users", userService.findAll());
        return "edit2"; // Шаблон для редактирования заказа
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        order.setId(id);
        orderService.save(order);
        return "redirect:/orders/list2";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders/list2"; // Переход после удаления
    }
}
