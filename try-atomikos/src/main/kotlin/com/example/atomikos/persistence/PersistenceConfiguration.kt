package com.example.atomikos.persistence

import com.example.atomikos.persistence.order.OrderEntity
import com.example.atomikos.persistence.stock.StockEntity
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [OrderEntity::class],
    entityManagerFactoryRef = "orderEntityManagerFactory",
    transactionManagerRef = "orderTransactionManager"
)
class OrderJpaConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.order")
    fun orderDataSourceProperties() = DataSourceProperties()

    @Bean
    fun orderDataSource(): DataSource = orderDataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    /**
     * @see
     * https://docs.spring.io/spring-data/jpa/reference/repositories/create-instances.html
     * https://stackoverflow.com/questions/48416927/spring-boot-required-a-bean-named-entitymanagerfactory-that-could-not-be-foun/54663039#54663039
     *
     * primary bean 이 중요함. query 도 primary 기준으로 setting 됨
     */
    @Bean
    @Primary
    fun orderEntityManagerFactory(
        @Qualifier("orderDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.setPackagesToScan("com.example.atomikos.persistence.order")
        factory.jpaVendorAdapter = vendorAdapter
        return factory
    }

    @Bean
    @Primary
    fun orderTransactionManager(
        @Qualifier("orderEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}

/**
 * delivery & stock
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [StockEntity::class],
    entityManagerFactoryRef = "stockEntityManagerFactory",
    transactionManagerRef = "stockTransactionManager"
)
class StockJpaConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.stock")
    fun stockDataSourceProperties() = DataSourceProperties()

    @Bean
    fun stockDataSource(): DataSource = stockDataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    @Bean
//    @Primary
    fun stockEntityManagerFactory(
        @Qualifier("stockDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan(
            "com.example.atomikos.persistence.stock"
        )
        return factory
    }

    @Bean
//    @Primary
    fun stockTransactionManager(
        @Qualifier("stockEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}