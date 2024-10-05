package com.example.atomikos.persistence.order

import com.atomikos.jdbc.AtomikosDataSourceBean
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDateTime

class OrderRepository(private val dataSource: AtomikosDataSourceBean) {

    fun save(order: OrderEntity): Long {
        val query = "INSERT INTO orders (order_name, order_date) VALUES (?, ?)"
        var generatedId: Long = -1

        dataSource.connection.use { connection ->
            val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            preparedStatement.setString(1, order.orderName)
            preparedStatement.setObject(2, order.orderDate)
            preparedStatement.executeUpdate()
            val keys = preparedStatement.generatedKeys
            if (keys.next()) {
                generatedId = keys.getLong(1)
            }
            preparedStatement.close()
        }
        return generatedId
    }

    fun findById(id: Long): OrderEntity? {
        val query = "SELECT id, order_name, order_date FROM orders WHERE id = ?"
        var order: OrderEntity? = null

        dataSource.connection.use { connection ->
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setLong(1, id)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                order = OrderEntity(
                    id = resultSet.getLong("id"),
                    orderName = resultSet.getString("order_name"),
                    orderDate = resultSet.getObject("order_date", LocalDateTime::class.java)
                )
            }
            resultSet.close()
            preparedStatement.close()
        }
        return order
    }

    fun count(): Int {
        val query = "SELECT COUNT(*) FROM orders"
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