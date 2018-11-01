package com.hat.test.acceptance

import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

@SpringBootConfiguration
@ComponentScan("com.hat.test.acceptance")
class TestConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Value('${hat.base-url}')
    String hatBaseUrl

    @Bean
    JdbcTemplate jdbcTemplate(
            @Value('${spring.datasource.username}') String username,
            @Value('${spring.datasource.password}') String password,
            @Value('${spring.datasource.url}') String url) {
        def dataSource = new DriverManagerDataSource()
        dataSource.setUrl(url)
        dataSource.setUsername(username)
        dataSource.setPassword(password)
        new JdbcTemplate(dataSource)
    }

    @Override
    void onApplicationEvent(ContextRefreshedEvent event) {
        RestAssured.baseURI = hatBaseUrl
    }
}
