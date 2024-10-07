package com.example.atomikos.persistence

import com.atomikos.spring.AtomikosDataSourceBean
import com.example.atomikos.persistence.order.OrderEntity
import com.example.atomikos.persistence.stock.StockEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [OrderEntity::class],
    entityManagerFactoryRef = "orderEntityManagerFactory",
    transactionManagerRef = "transactionManager"
)
class OrderJpaConfig {

    @Bean
    @ConfigurationProperties("spring.jta.atomikos.datasource.order")
    fun orderDataSource(): AtomikosDataSourceBean = AtomikosDataSourceBean()

    @Bean
    fun jpaProperties(): JpaProperties = JpaProperties()

    @Bean
    fun hibernateProperties(): HibernateProperties = HibernateProperties()

    @Bean
    @Primary
    fun orderEntityManagerFactory(
        @Qualifier("orderDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {
        val builder = EntityManagerFactoryBuilder(
            HibernateJpaVendorAdapter(),
            hibernateProperties().determineHibernateProperties(jpaProperties().properties, HibernateSettings()),
            null
        )
        return builder
            .dataSource(dataSource)
            .packages("com.example.atomikos.persistence.order")
            .jta(true)
            .build()
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
    transactionManagerRef = "transactionManager"
)
class StockJpaConfig {

    @Bean
    @ConfigurationProperties("spring.jta.atomikos.datasource.stock")
    fun stockDataSource(): AtomikosDataSourceBean = AtomikosDataSourceBean()

    @Bean
    @Primary
    fun stockEntityManagerFactory(
        jpaProperties: JpaProperties,
        hibernateProperties: HibernateProperties,
        @Qualifier("stockDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {
        val builder = EntityManagerFactoryBuilder(
            HibernateJpaVendorAdapter(),
            hibernateProperties.determineHibernateProperties(jpaProperties.properties, HibernateSettings()),
            null
        )
        return builder
            .dataSource(dataSource)
            .packages("com.example.atomikos.persistence.stock")
            .jta(true)
            .build()
    }
}