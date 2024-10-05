package com.example.atomikos.persistence.stock

import com.atomikos.jdbc.AtomikosDataSourceBean
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class StockRepository(private val dataSource: AtomikosDataSourceBean) {

    fun save(stock: StockEntity): Long {
        val query = "INSERT INTO stocks (product_id, quantity) VALUES (?, ?)"
        var generatedId: Long = -1

        dataSource.connection.use { connection ->
            val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            preparedStatement.setInt(1, stock.productId)
            preparedStatement.setInt(2, stock.quantity)
            preparedStatement.executeUpdate()
            val keys = preparedStatement.generatedKeys
            if (keys.next()) {
                generatedId = keys.getLong(1)
            }
            preparedStatement.close()
        }
        return generatedId
    }

    fun findById(id: Long): StockEntity? {
        val query = "SELECT id, product_id, quantity FROM stocks WHERE id = ?"
        var stock: StockEntity? = null

        dataSource.connection.use { connection ->
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setLong(1, id)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                stock = StockEntity(
                    id = resultSet.getLong("id"),
                    productId = resultSet.getInt("product_id"),
                    quantity = resultSet.getInt("quantity")
                )
            }
            resultSet.close()
            preparedStatement.close()
        }
        return stock
    }

    fun count(): Int {
        val query = "SELECT COUNT(*) FROM stocks"
        var count = 0

        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery(query)
            if (resultSet.next()) {
                count = resultSet.getInt(1)
            }
            resultSet.close()
            statement.close()
        }
        return count
    }
}