package com.example.atomikos.controller

import com.example.atomikos.service.FragileOrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val service: FragileOrderService,
    private val objectMapper: ObjectMapper,
) {
    @PostMapping("/orders")
    fun placeOrder(): ResponseEntity<String> {
        return ResponseEntity.ok("successfully ordered")
    }
}