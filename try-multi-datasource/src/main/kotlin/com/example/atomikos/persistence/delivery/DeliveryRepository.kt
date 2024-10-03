package com.example.atomikos.persistence.delivery

import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository: JpaRepository<DeliveryEntity, Long> {
}