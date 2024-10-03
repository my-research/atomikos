package com.example.atomikos.service

import com.example.atomikos.persistence.order.OrderEntity
import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockEntity
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
    fun order(productId: Int) {

        // 1. create Order
        val order = OrderEntity(orderName = "$productId 의 주문")
        orderRepository.save(order)
        logger.info { "[order] $order 생성됨" }

        // 2. decrease Stock
        val stock = getStock(productId)
        logger.info { "[stock] $stock 조회됨" }
        stock.decrease()
        stockRepository.save(stock)
        logger.info { "[stock] $stock 저장됨" }
    }

    fun order2(productId: Int) {
        // 1. create Order
        val order = OrderEntity(orderName = "$productId 의 주문")
        orderRepository.save(order)

        val randomNumber = (1..5000).random()
        stockRepository.save(StockEntity(productId = randomNumber, quantity = 100))
    }

    private fun getStock(productId: Int) = stockRepository.findByProductId(productId).orElseThrow {
        NoSuchElementException("${productId}'s stock not found.")
    }
}