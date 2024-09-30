package com.example.atomikos.infra.delivery

import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository: JpaRepository<DeliveryEntity, Long> {
}