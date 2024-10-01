package com.example.atomikos.infra.order

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

    // externalized property binding 을 통해 dataSourceProperty 를 setting
    @Bean
    @ConfigurationProperties("spring.datasource.order")
    fun orderDataSourceProperties() = DataSourceProperties()

    /**
     * 앞서 주입받은 dataSource properties 를 기반으로 dataSource 생성
     *
     * for connection pool & manging connections
     */
    //
    @Bean
    fun orderDataSource(): DataSource = orderDataSourceProperties()
        .initializeDataSourceBuilder()
        .build()

    /**
     * @see
     * https://docs.spring.io/spring-data/jpa/reference/repositories/create-instances.html
     * https://stackoverflow.com/questions/48416927/spring-boot-required-a-bean-named-entitymanagerfactory-that-could-not-be-foun/54663039#54663039
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
        factory.setPackagesToScan("com.example.atomikos.infra.order")
        factory.jpaVendorAdapter = vendorAdapter
        return factory
    }

    @Bean
    fun orderTransactionManager(entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}