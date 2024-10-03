package com.example.atomikos.persistence.delivery

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "deliveries")
data class DeliveryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "delivery_name", nullable = false, length = 255)
    val deliveryName: String,

    @Column(name = "delivery_date", nullable = false, updatable = false)
    val deliveryDate: LocalDateTime = LocalDateTime.now()
)