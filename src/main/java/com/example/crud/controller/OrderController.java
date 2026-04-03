package com.example.crud.controller;

import com.example.crud.dto.OrderDto;
import com.example.crud.dto.OrderItemDto;
import com.example.crud.entity.OrderItem;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/orders")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderDto toOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalPrice(order.getTotalPrice());

        List<OrderItemDto> itemDtos = order.getItems().stream().map(item -> {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setProductPrice(item.getProduct().getPrice());
            itemDto.setQuantity(item.getQuantity());
            return itemDto;
        }).toList();

        dto.setItems(itemDtos);
        return dto;
    }

    // Создание заказа
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @RequestBody List<OrderItemDto> itemsDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(new ArrayList<>());

        for (OrderItemDto dto : itemsDto) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            order.addItem(item);
        }

        order.calcAndSetTotalPrice();
        Order savedOrder = orderRepository.save(order);

        OrderDto dto = toOrderDto(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Список заказов пользователя
    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrder(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> dtos = orders.stream().map(this::toOrderDto).toList();
        return ResponseEntity.ok(dtos);
    }

    // Заказы пользователя за период
    @GetMapping("/range")
    public ResponseEntity<List<OrderDto>> getUserOrderInRange(@PathVariable Long userId, @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        List<Order> orders = orderRepository.findByUserIdAndCreatedAtBetween(userId, from, to);
        List<OrderDto> dtos = orders.stream().map(this::toOrderDto).toList();
        return ResponseEntity.ok(dtos);
    }
}
