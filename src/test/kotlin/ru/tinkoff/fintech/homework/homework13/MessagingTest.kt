package ru.tinkoff.fintech.homework.homework13

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import ru.tinkoff.fintech.homework.homework13.components.MessageProducer
import ru.tinkoff.fintech.homework.homework13.enum.Status
import ru.tinkoff.fintech.homework.homework13.repository.JdbcEventDao

@SpringBootTest
class MessagingTest() : FeatureSpec() {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var messageProducer: MessageProducer

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeSpec(spec: Spec) {
        jdbcTemplate.execute(INSERT_QUERY)
    }

    init {
        feature("check") {
            scenario("success") {
                messageProducer.produce()
                delay(5000)
                val event = jdbcTemplate.queryForObject(
                    SELECT_QUERY,
                    JdbcEventDao.EventMapper
                )
                event.status shouldBe Status.DONE
            }
        }
    }

    private companion object {
        private const val ID = 3L
        private const val INSERT_QUERY = "insert into events values ($ID, 'SMS', 'sms text', 'NEW')"
        private const val SELECT_QUERY = "select id, type, body, status from events where id = $ID"
    }

}

