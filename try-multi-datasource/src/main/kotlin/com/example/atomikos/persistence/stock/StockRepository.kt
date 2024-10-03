package com.example.atomikos.persistence.stock

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository: JpaRepository<StockEntity, Long> {
}