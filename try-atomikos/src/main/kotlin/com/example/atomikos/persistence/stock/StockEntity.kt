package com.example.atomikos.persistence.stock

data class StockEntity(
    val id: Long? = null,
    val productId: Int,
    var quantity: Int,
) {
    fun decrease() {
        this.quantity -= 1
    }
}