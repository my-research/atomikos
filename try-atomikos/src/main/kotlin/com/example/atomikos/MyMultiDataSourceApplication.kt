package com.example.atomikos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyMultiDataSourceApplication

fun main(args: Array<String>) {
  runApplication<MyMultiDataSourceApplication>(*args)
}
