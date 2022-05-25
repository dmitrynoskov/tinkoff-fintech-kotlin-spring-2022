package ru.tinkoff.fintech.homework.homework13.components

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework13.enum.Status
import ru.tinkoff.fintech.homework.homework13.repository.JdbcEventDao
import javax.jms.JMSException

@Service
class MessageProducer(private val jmsTemplate: JmsTemplate, private val jdbcEventDao: JdbcEventDao) {

    fun produce() {
        val events = jdbcEventDao.getEventsWithStatus(Status.NEW)
        events.forEach {
            try {
                jmsTemplate.convertAndSend("eventQueue",it)
                jdbcEventDao.updateStatus(it.id, Status.IN_PROCESS)
            } catch (jmsException: JMSException) {
                jdbcEventDao.updateStatus(it.id, Status.ERROR)
            }
        }
    }

}
