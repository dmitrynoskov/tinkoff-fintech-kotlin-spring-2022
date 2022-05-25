package ru.tinkoff.fintech.homework.homework13

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableJms
@SpringBootApplication
class Application

fun main() {
    runApplication<Application>()
}
