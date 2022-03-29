package ru.tinkoff.fintech.homework.homework06

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.homework06.model.Person
import ru.tinkoff.fintech.homework.homework06.service.client.PersonInformationClient
import java.time.LocalDate
import java.time.Month
import kotlin.text.Charsets.UTF_8

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var personInformationClient: PersonInformationClient

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { personInformationClient.getPerson(Anna.passportNumber) } returns Anna
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("add person") {
            scenario("success") {
                val person = getPersonByPassportNumber(Anna.passportNumber)
                person shouldBe Anna
            }
            scenario("failure - wrong passport number format") {

            }
            scenario("failure - person's data not found") {

            }
        }
        feature("get person") {
            scenario("success") {

            }
            scenario("failure - person is not found in database") {

            }
        }
        feature("get paginated list") {
            scenario("success") {

            }
            scenario("failure - wrong pagination format") {

            }
        }
    }

    private fun addPerson(passportNumber: Int): Person =
        mockMvc.post("/add").readResponse()

    private fun getPersonByPassportNumber(passportNumber: Int): Person =
        mockMvc.get("/get/${passportNumber}").readResponse()

    private fun findPersonsBySurnameWithPagination(surname: String, pageSize: Int, page: Int): List<Person> =
        mockMvc.get("/find?surname=${surname}&pageSize=${pageSize}&page=${page}").readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }.andReturn().response.getContentAsString(UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val Anna = Person("Anna", "James", LocalDate.of(2000, Month.JANUARY, 10), "000000".toInt())
    private val Maria = Person("Maria", "Anderson", LocalDate.of(1998, Month.OCTOBER, 5), "111111".toInt())
    private val Julia = Person("Julia", "Robins", LocalDate.of(2001, Month.JANUARY, 30), "222222".toInt())
    private val Andrew = Person("Andrew", "James", LocalDate.of(1995, Month.FEBRUARY, 1), "333333".toInt())
    private val Jacob = Person("Jacob", "Fill", LocalDate.of(1999, Month.MARCH, 18), "444444".toInt())
    private val Oscar = Person("Oscar", "Wild", LocalDate.of(2000, Month.AUGUST, 9), "555555".toInt())
}

/*
@PostMapping("/add")
    fun addPerson(@RequestBody passportNumber: Int): Person =
        personService.addPerson(passportNumber)

    @GetMapping("/get/{passportNumber}")
    fun getPersonByPassportNumber(@PathVariable passportNumber: Int): Person =
        personService.getPersonByPassportNumber(passportNumber)

    @GetMapping("/find")
    fun findPersonsBySurnameWithPagination(
        @RequestParam surname: String,
        @RequestParam pageSize: Int,
        @RequestParam page: Int
    ): List<Person> =
        personService.findPersonsBySurnameWithPagination(surname, pageSize, page)


 */