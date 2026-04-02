package com.example.crud.controller;

import com.example.crud.dto.OrderItemDto;
import com.example.crud.entity.User;
import com.example.crud.entity.Order;
import com.example.crud.entity.Product;
import com.example.crud.repository.UserRepository;
import com.example.crud.repository.OrderRepository;
import com.example.crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/users/{userId}/orders")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Создание заказа для пользователя
    @PostMapping
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody List<OrderItemDto> items) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Список заказов пользователя
    @GetMapping
    public ResponseEntity<List<Order>> getUserOrder(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // Заказы пользователя за период
    @GetMapping("/range")
    public ResponseEntity<List<Order>> getUserOrderInRange(@PathVariable Long userId, @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        List<Order> orders = orderRepository.findByUserIdAndCreatedTime(userId, from, to);
        return ResponseEntity.ok(orders);
    }
}
