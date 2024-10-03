package com.example.atomikos

import com.example.atomikos.persistence.order.OrderRepository
import com.example.atomikos.persistence.stock.StockRepository
import com.example.atomikos.service.FragileOrderService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MyMultiDataSourceApplicationTests {

  @Autowired
  lateinit var sut: FragileOrderService

  @Autowired
  lateinit var orderRepository: OrderRepository

  @Autowired
  lateinit var stockRepository: StockRepository

  @Test
  fun `주문하면 order 가 하나 생기고 stock 이 차감되어야 함`() {
    val beforeOrderCount = orderRepository.count()
    val beforeOrderStockQuantity = stockRepository.findByProductId(1).get().quantity

    sut.order(1)

    val afterOrderCount = orderRepository.count()
    val afterOrderStockQuantity = stockRepository.findByProductId(1).get().quantity

    afterOrderCount shouldBe beforeOrderCount + 1
    afterOrderStockQuantity shouldBe beforeOrderStockQuantity - 1
  }

  @Test
  fun `혹시 몰라 stock 도 insert 해봄`() {
    val beforeOrderCount = orderRepository.count()
    val beforeOrderStockQuantity = stockRepository.count()

    sut.order2(1)

    val afterOrderCount = orderRepository.count()
    val afterOrderStockQuantity = stockRepository.count()

    afterOrderCount shouldBe beforeOrderCount + 1
    afterOrderStockQuantity shouldBe beforeOrderStockQuantity + 1
  }
}
