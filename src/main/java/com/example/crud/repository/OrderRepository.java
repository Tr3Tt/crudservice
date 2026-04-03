package com.example.crud.repository;

import com.example.crud.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime from, LocalDateTime to);
}
