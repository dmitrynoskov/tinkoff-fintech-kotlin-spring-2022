package ru.tinkoff.fintech.homework.homework13.components

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework13.dto.EventDto
import ru.tinkoff.fintech.homework.homework13.enum.Status
import ru.tinkoff.fintech.homework.homework13.enum.Type
import ru.tinkoff.fintech.homework.homework13.repository.JdbcEventDao
import ru.tinkoff.fintech.homework.homework13.handler.EmailService
import ru.tinkoff.fintech.homework.homework13.handler.PushService
import ru.tinkoff.fintech.homework.homework13.handler.SmsService

@Service
class MessageConsumer(
    private val emailService: EmailService,
    private val pushService: PushService,
    private val smsService: SmsService,
    private val jdbcEventDao: JdbcEventDao,
) {

    @JmsListener(destination = "eventQueue")
    fun consume(event: EventDto) {
        when (event.type) {
            Type.EMAIL -> emailService.send(event.body)
            Type.PUSH -> pushService.send(event.body)
            Type.SMS -> smsService.send(event.body)
        }
        jdbcEventDao.updateStatus(event.id, Status.DONE)
    }
}
