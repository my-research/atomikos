package com.example.atomikos.controller

import com.example.atomikos.infra.delivery.DeliveryEntity
import com.example.atomikos.infra.delivery.DeliveryRepository
import com.example.atomikos.infra.order.OrderEntity
import com.example.atomikos.infra.order.OrderRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderController(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository,
) {

    @PostMapping("/orders")
    fun placeOrder(): ResponseEntity<String> {

        val savedOrder = orderRepository.save(OrderEntity(orderName = "자동차 주문"))

        deliveryRepository.save(DeliveryEntity(deliveryName = "${savedOrder.id} 의 주문"))

        return ResponseEntity.ok("successfully ordered")
    }

}