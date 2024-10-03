package com.example.atomikos.persistence.stock

import jakarta.persistence.*

@Entity
@Table(name = "stocks")
data class StockEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "productId")
    val productId: Int,

    @Column(name = "quantity")
    val quantity: Int,
)