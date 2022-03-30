package ru.tinkoff.fintech.homework.homework06

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.homework06.model.Person
import ru.tinkoff.fintech.homework.homework06.service.client.PersonInformationClient
import ru.tinkoff.fintech.homework.homework06.service.repository.PersonDatabase
import java.time.LocalDate
import java.time.Month
import kotlin.text.Charsets.UTF_8

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var personInformationClient: PersonInformationClient

    @SpykBean
    private lateinit var personDatabase: PersonDatabase

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeTest(testCase: TestCase) {
        presavePersonsInDatabase()
        every { personInformationClient.getPerson(AnnaJames.passportNumber) } returns AnnaJames
    }

    init {
        feature("add person") {
            scenario("success") {
                val person = addPerson(AnnaJames.passportNumber)
                person shouldBe AnnaJames
            }
            scenario("failure - wrong passport number format") {

            }
            scenario("failure - person's data not found") {

            }
        }
        feature("get person") {
            scenario("success") {
                val person = getPersonByPassportNumber(AnnaJames.passportNumber)
                person shouldBe AnnaJames
            }
            scenario("failure - person is not found in database") {
                mockMvc.get("/get/{passportNumber}", unknownPassportNumber)
                    .andExpect { status { isEqualTo(HttpStatus.BAD_REQUEST.value()) } }
            }
        }
        feature("get paginated list") {
            scenario("success") {
                val actualList = findPersonsByNameWithPagination("Anna", 2, 2)
                val expectedList = listOf(AnnaJames, AnnaPorch)
                actualList shouldBe expectedList
            }
            scenario("failure - wrong pagination format") {
                mockMvc.get("/find?name={name}&pageSize={pageSize}&page={page}", "Anna", 0, 1)
                    .andExpect { status { isEqualTo(HttpStatus.BAD_REQUEST.value()) } }
                mockMvc.get("/find?name={name}&pageSize={pageSize}&page={page}", "Anna", 1, 0)
                    .andExpect { status { isEqualTo(HttpStatus.BAD_REQUEST.value()) } }
            }
        }
    }

    private fun addPerson(passportNumber: String): Person =
        mockMvc.post("/add") { contentType = MediaType.TEXT_PLAIN; content = passportNumber }.readResponse()

    private fun getPersonByPassportNumber(passportNumber: String): Person =
        mockMvc.get("/get/{passportNumber}", passportNumber).readResponse()

    private fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> =
        mockMvc.get("/find?name={name}&pageSize={pageSize}&page={page}", name, pageSize, page).readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }.andReturn().response.getContentAsString(UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val AnnaJames = Person("Anna", "James", LocalDate.of(2000, Month.JANUARY, 10), "000000")
    private val AnnaDias = Person("Anna", "Dias", LocalDate.of(2001, Month.NOVEMBER, 19), "111111")
    private val AnnaPorch = Person("Anna", "Porch", LocalDate.of(1995, Month.JUNE, 8), "222222")
    private val AnnaAnderson = Person("Anna", "Anderson", LocalDate.of(1998, Month.OCTOBER, 5), "333333")
    private val AnnaRobins = Person("Anna", "Robins", LocalDate.of(2001, Month.JANUARY, 30), "444444")
    private val AndrewJames = Person("Andrew", "James", LocalDate.of(1995, Month.FEBRUARY, 1), "555555")
    private val JacobFill = Person("Jacob", "Fill", LocalDate.of(1999, Month.MARCH, 18), "666666")
    private val OscarWild = Person("Oscar", "Wild", LocalDate.of(2000, Month.AUGUST, 9), "777777")

    private val unknownPassportNumber = "999999"
    private val illegalPassportNumberLength = "10"
    private val negativePassportNumber = "-111111"

    private fun presavePersonsInDatabase() {
        personDatabase.savePerson(AnnaJames)
        personDatabase.savePerson(AnnaDias)
        personDatabase.savePerson(AnnaPorch)
        personDatabase.savePerson(AnnaAnderson)
        personDatabase.savePerson(AnnaRobins)
        personDatabase.savePerson(AndrewJames)
        personDatabase.savePerson(JacobFill)
        personDatabase.savePerson(OscarWild)
    }
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