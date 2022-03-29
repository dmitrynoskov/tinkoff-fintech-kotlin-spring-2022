package ru.tinkoff.fintech.homework.homework06

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import ru.tinkoff.fintech.homework.homework06.Application
import ru.tinkoff.fintech.homework.homework06.service.client.PersonInformationClient
import kotlin.reflect.KClass

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockBean
    private lateinit var personInformationClient: PersonInformationClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {

    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("add person") {
            scenario("f") {
                true
            }
        }
    }


}