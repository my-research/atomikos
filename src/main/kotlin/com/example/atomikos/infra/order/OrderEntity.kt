package com.example.atomikos.infra.order

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "order_name", nullable = false, length = 255)
    val orderName: String,

    @Column(name = "order_date", nullable = false, updatable = false)
    val orderDate: LocalDateTime = LocalDateTime.now()
)