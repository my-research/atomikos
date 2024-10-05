package com.example.atomikos

import com.example.atomikos.persistence.PersistenceConfiguration
import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockRepository

class TryAtomikosApplication

fun main(args: Array<String>) {
    val orderRepository = OrderRepository(PersistenceConfiguration.orderDataSource)
    val stockRepository = StockRepository(PersistenceConfiguration.stockDataSource)

    val orderCount = orderRepository.count()
    println("Order count: $orderCount")

    val stockCount = stockRepository.count()
    println("Stock count: $stockCount")
}
