package com.example.atomikos.persistence.stock

import jakarta.persistence.*

@Entity
@Table(name = "stocks")
data class StockEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "product_id")
    val productId: Int,

    @Column(name = "quantity")
    var quantity: Int,
) {
    fun decrease() {
        this.quantity -= 1
    }
}