package ru.tinkoff.fintech.homework.homework13.configuration

import org.apache.activemq.command.ActiveMQQueue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActiveMQConfiguration {

    @Bean
    fun getQueue() = ActiveMQQueue("eventQueue")

}
