package com.example.atomikos.infra.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<OrderEntity, Long> {
}