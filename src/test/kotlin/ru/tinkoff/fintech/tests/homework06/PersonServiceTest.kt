package ru.tinkoff.fintech.tests.homework06

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import kotlinx.coroutines.delay
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.random.Random.Default.nextInt
import kotlin.text.Charsets.UTF_8
import ru.tinkoff.fintech.homework.homework06.service.PersonService
import ru.tinkoff.fintech.homework.homework06.service.client.PersonInformationClient
import ru.tinkoff.fintech.homework.homework06.Application

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper): FeatureSpec() {

    @MockBean
    private lateinit var personInformationClient: PersonInformationClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {

    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("add person"){
            scenario("f") {
                true
            }
        }
    }



}
