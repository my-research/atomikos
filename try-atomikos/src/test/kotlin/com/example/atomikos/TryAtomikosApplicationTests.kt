package com.example.atomikos

import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockRepository
import com.example.atomikos.persistence.PersistenceConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TryAtomikosApplicationTests {

    private val orderRepository = OrderRepository(PersistenceConfiguration.orderDataSource)
    private val stockRepository = StockRepository(PersistenceConfiguration.stockDataSource)

    @Test
    fun testOrderCount() {
        val orderCount = orderRepository.count()
        println("Order count: $orderCount")
        assertTrue(orderCount >= 0, "Order count should be non-negative")
    }

    @Test
    fun testStockCount() {
        val stockCount = stockRepository.count()
        println("Stock count: $stockCount")
        assertTrue(stockCount >= 0, "Stock count should be non-negative")
    }
}