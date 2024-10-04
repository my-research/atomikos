package com.example.atomikos.persistence

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
    var quantity: Int,
) {
    fun decrease() {
        this.quantity -= 1
    }
}