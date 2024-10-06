package com.example.atomikos.persistence.stock

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StockRepository: JpaRepository<StockEntity, Long> {
    fun findByProductId(id: Int): Optional<StockEntity>
}