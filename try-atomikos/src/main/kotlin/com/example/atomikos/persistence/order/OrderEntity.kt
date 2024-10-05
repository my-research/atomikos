package com.example.atomikos.persistence.order

import java.time.LocalDateTime

data class OrderEntity(
    val id: Long? = null,
    val orderName: String,
    val orderDate: LocalDateTime = LocalDateTime.now()
)