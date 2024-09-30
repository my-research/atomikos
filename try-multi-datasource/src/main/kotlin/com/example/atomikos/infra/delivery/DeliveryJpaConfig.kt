package com.example.atomikos.infra.delivery

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    basePackageClasses = [DeliveryEntity::class],
    entityManagerFactoryRef = "deliveryEntityManagerFactory",
    transactionManagerRef = "deliveryTransactionManager"
)
class DeliveryJpaConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.delivery")
    fun deliveryDataSourceProperties() = DataSourceProperties()

    @Bean
    fun deliveryDataSource(): DataSource = deliveryDataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    @Bean
    fun deliveryEntityManagerFactory(
        @Qualifier("deliveryDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {

        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.example.atomikos.infra.delivery")
        return factory
    }

    @Bean
    fun deliveryTransactionManager(
        @Qualifier("deliveryEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}