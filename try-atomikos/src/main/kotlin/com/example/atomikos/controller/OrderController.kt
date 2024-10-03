package com.example.atomikos.controller

import com.example.atomikos.persistence.delivery.DeliveryEntity
import com.example.atomikos.persistence.delivery.DeliveryRepository
import com.example.atomikos.persistence.order.OrderEntity
import com.example.atomikos.persistence.order.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository,
    private val objectMapper: ObjectMapper,
) {

    @PostMapping("/orders")
    fun placeOrder(): ResponseEntity<String> {

        val savedOrder = orderRepository.save(OrderEntity(orderName = "자동차 주문"))

        deliveryRepository.save(DeliveryEntity(deliveryName = "${savedOrder.id} 의 주문"))

        return ResponseEntity.ok("successfully ordered")
    }

    @GetMapping("/")
    fun getAll(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "orders" to objectMapper.writeValueAsString(orderRepository.findAll()),
            "deliveries" to objectMapper.writeValueAsString(deliveryRepository.findAll())
        ))
    }
}