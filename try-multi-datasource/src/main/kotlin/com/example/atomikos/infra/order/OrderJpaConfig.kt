package com.example.atomikos.infra.order

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
    basePackageClasses = [OrderEntity::class],
    entityManagerFactoryRef = "entityManagerFactory",
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
     * https://stackoverflow.com/questions/48416927/spring-boot-required-a-bean-named-entitymanagerfactory-that-could-not-be-foun/54663039#54663039
     */
    @Bean(name= ["entityManagerFactory"])
    fun orderEntityManagerFactory(
        @Qualifier("orderDataSource") dataSource: DataSource?,
    ): LocalContainerEntityManagerFactoryBean {


        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.setPackagesToScan("com.example.atomikos.infra.order")
        return factory
    }

    @Bean
    fun orderTransactionManager(entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}