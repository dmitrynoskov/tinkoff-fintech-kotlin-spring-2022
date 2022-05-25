package ru.tinkoff.fintech.homework.homework13.components

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ChronService(private val messageProducer: MessageProducer) {

    @Scheduled(cron = "\${cron}")
    fun send() {
        messageProducer.produce()
    }

}
