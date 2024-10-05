package com.example.atomikos.persistence

import com.atomikos.jdbc.AtomikosDataSourceBean
import java.util.*


object PersistenceConfiguration {

    val orderDataSource = initOrder()
    val stockDataSource = initStock()

    private fun initOrder(): AtomikosDataSourceBean {
        val dataSource = AtomikosDataSourceBean()
        dataSource.uniqueResourceName = "orderDataSource"

        val properties = Properties()
        properties.setProperty("url", "jdbc:postgresql://localhost:5432/orderdb")
        properties.setProperty("user", "orderuser")
        properties.setProperty("password", "orderpassword")
        properties.setProperty("driverClassName", "org.postgresql.Driver")
        dataSource.xaProperties = properties
        dataSource.xaDataSourceClassName = "org.postgresql.xa.PGXADataSource"

        return dataSource
    }

    private fun initStock(): AtomikosDataSourceBean {
        val dataSource = AtomikosDataSourceBean()
        dataSource.uniqueResourceName = "deliveryDataSource"

        val properties = Properties()
        properties.setProperty("url", "jdbc:mysql://localhost:3306/deliverydb")
        properties.setProperty("user", "deliveryuser")
        properties.setProperty("password", "deliverypassword")
        properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver")
        dataSource.xaProperties = properties
        dataSource.xaDataSourceClassName = "com.mysql.cj.jdbc.MysqlXADataSource"

        return dataSource
    }
}
