package com.example.atomikos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AtomikosApplication

fun main(args: Array<String>) {
  runApplication<AtomikosApplication>(*args)
}
