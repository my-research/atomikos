package com.example.atomikos.controller

import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryController(
    private val orderRepository: OrderRepository,
    private val stockRepository: StockRepository,
    private val objectMapper: ObjectMapper,
) {
    @GetMapping("/")
    fun getAll(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "orders" to objectMapper.writeValueAsString(orderRepository.findAll()),
            "stocks" to objectMapper.writeValueAsString(stockRepository.findAll())
        ))
    }
}