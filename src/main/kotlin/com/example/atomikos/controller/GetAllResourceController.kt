package com.example.atomikos.controller

import com.example.atomikos.infra.delivery.DeliveryRepository
import com.example.atomikos.infra.order.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAllResourceController(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository,
    private val objectMapper: ObjectMapper,
) {
    @GetMapping("/")
    fun getAll(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "orders" to objectMapper.writeValueAsString(orderRepository.findAll()),
            "deliveries" to objectMapper.writeValueAsString(deliveryRepository.findAll())
        ))
    }
}