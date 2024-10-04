package com.example.atomikos.persistence


data class StockEntity(
    val id: Long? = null,
    val productId: Int,
    var quantity: Int,
) {
    fun decrease() {
        this.quantity -= 1
    }
}