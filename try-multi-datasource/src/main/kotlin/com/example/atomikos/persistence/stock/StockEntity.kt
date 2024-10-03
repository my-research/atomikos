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
    var quantity: Int,
) {
    fun decrease() {
//        throw IllegalStateException("재고가 존재하지 않습니다")
        this.quantity -= 1
    }
}