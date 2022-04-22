package ru.tinkoff.fintech.homework.homework09.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ServiceConfiguration {

    @Bean
    fun webClientConfig(): WebClient {
        return WebClient.builder()
            .build()
    }

}

private const val CONNECT_TIMEOUT_IN_SECONDS = 30L
private const val READ_TIMEOUT_IN_SECONDS = 60L
