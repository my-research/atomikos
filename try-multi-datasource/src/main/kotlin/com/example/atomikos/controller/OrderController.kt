package com.example.atomikos.controller

import com.example.atomikos.service.FragileOrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val service: FragileOrderService,
) {
    @PostMapping("/orders")
    fun placeOrder(@RequestBody body: Map<String, String>): ResponseEntity<String> {

        val productId = body["productId"]!!
        service.order(productId.toInt())

        return ResponseEntity.ok("successfully ordered")
    }
}