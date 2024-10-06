package com.example.atomikos.service

import com.example.atomikos.persistence.order.OrderEntity
import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockRepository
import com.example.atomikos.util.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FragileOrderService(
    private val stockRepository: StockRepository,
    private val orderRepository: OrderRepository,
) {

    /**
     * 주문을 일단 생성하고 stock 을 나중에 차감
     *
     * stock 이 없으면 주문을 rollback
     */
    @Transactional
    fun order(productId: Int, throwingFlag: Boolean = false) {

        // 1. create Order
        val order = OrderEntity(orderName = "$productId 의 주문")
        orderRepository.save(order)
        logger.info { "[order] $order 생성됨" }

        // 2. decrease Stock
        val stock = getStock(productId)
        stock.decrease()
        stockRepository.save(stock)

        throwWhether(throwingFlag)

        logger.info { "[stock] $stock 저장됨" }
    }

    private fun throwWhether(throwing: Boolean) {
        if (throwing) throw IllegalStateException("exception thrown while saving stock!!")
    }

    private fun getStock(productId: Int) = stockRepository.findByProductId(productId).orElseThrow {
        NoSuchElementException("${productId}'s stock not found.")
    }
}