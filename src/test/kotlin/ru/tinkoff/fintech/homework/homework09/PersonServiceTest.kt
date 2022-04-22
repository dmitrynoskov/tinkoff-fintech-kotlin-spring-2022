package ru.tinkoff.fintech.homework.homework09

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.homework09.model.Person
import ru.tinkoff.fintech.homework.homework09.repository.UserDao
import ru.tinkoff.fintech.homework.homework09.service.client.PersonInformationClient
import java.time.LocalDate
import java.time.Month
import kotlin.text.Charsets.UTF_8

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    private lateinit var personInformationClient: PersonInformationClient

    private lateinit var userDao: UserDao

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeTest(testCase: TestCase) {
        coEvery {personInformationClient.getPerson(AnnaJames.passportNumber) } returns AnnaJames
        coEvery { personInformationClient.getPerson(OscarWild.passportNumber) } returns OscarWild
        coEvery { personInformationClient.getPerson(JacobJackson.passportNumber) } returns JacobJackson
        coEvery { personInformationClient.getPerson(notFoundInClientPassportNumber) } returns null
    }

    init {
        feature("add person") {
            scenario("success - addition new person") {
                getStatusCodeOfGetPersonByPassportNumber(OscarWild.passportNumber) shouldBe HttpStatus.BAD_REQUEST.value()
                runBlocking {
                    addPerson(OscarWild.passportNumber)
                }
                getStatusCodeOfGetPersonByPassportNumber(OscarWild.passportNumber) shouldBe HttpStatus.OK.value()
            }
            scenario("success - updating person in database") {
                JacobFill.passportNumber shouldBe JacobJackson.passportNumber
                val oldPerson = getPersonByPassportNumber(JacobFill.passportNumber)
                oldPerson shouldBe JacobFill
                addPerson(JacobJackson.passportNumber)
                val newPerson = getPersonByPassportNumber(JacobJackson.passportNumber)
                newPerson shouldBe JacobJackson
            }
            scenario("failure - wrong passport number format") {
                getStatusCodeOfAddPerson(illegalPassportNumberLength) shouldBe HttpStatus.BAD_REQUEST.value()
                getStatusCodeOfAddPerson(negativePassportNumber) shouldBe HttpStatus.BAD_REQUEST.value()
            }
            scenario("failure - person's data not found") {
                getStatusCodeOfAddPerson(notFoundInClientPassportNumber) shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
            }
        }
        feature("get person") {
            scenario("success") {
                val person = getPersonByPassportNumber(AnnaJames.passportNumber)
                person shouldBe AnnaJames
            }
            scenario("failure - person is not found in database") {
                getStatusCodeOfGetPersonByPassportNumber(notInDatabasePassportNumber) shouldBe HttpStatus.BAD_REQUEST.value()
            }
        }
    }

    private fun addPerson(passportNumber: String) =
        mockMvc.post("/add") { contentType = MediaType.TEXT_PLAIN; content = passportNumber }

    private fun getStatusCodeOfAddPerson(passportNumber: String): Int =
        mockMvc.post("/add") { contentType = MediaType.TEXT_PLAIN; content = passportNumber }
            .andReturn().response.status

    private fun getPersonByPassportNumber(passportNumber: String) : Person =
        mockMvc.get("/get/{passportNumber}", passportNumber).readResponse()

    private fun getStatusCodeOfGetPersonByPassportNumber(passportNumber: String): Int =
        mockMvc.get("/get/{passportNumber}", passportNumber).andReturn().response.status

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T =
        this.andExpect { status { isEqualTo(expectedStatus.value()) } }.andReturn().response.getContentAsString(UTF_8)
            .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private companion object {
        private val AnnaJames = Person("Anna", "James", LocalDate.of(2000, Month.JANUARY, 10), "000000")
        private val AnnaDias = Person("Anna", "Dias", LocalDate.of(2001, Month.NOVEMBER, 19), "111111")
        private val AnnaPorch = Person("Anna", "Porch", LocalDate.of(1995, Month.JUNE, 8), "222222")
        private val AnnaAnderson = Person("Anna", "Anderson", LocalDate.of(1998, Month.OCTOBER, 5), "333333")
        private val AnnaRobins = Person("Anna", "Robins", LocalDate.of(2001, Month.JANUARY, 30), "444444")
        private val AndrewJames = Person("Andrew", "James", LocalDate.of(1995, Month.FEBRUARY, 1), "555555")
        private val JacobFill = Person("Jacob", "Fill", LocalDate.of(1999, Month.MARCH, 18), "666666")
        private val JacobJackson = Person("Jacob", "Jackson", LocalDate.of(1999, Month.MARCH, 18), "666666")
        private val OscarWild = Person("Oscar", "Wild", LocalDate.of(2000, Month.AUGUST, 9), "777777")

        private const val notInDatabasePassportNumber = "999999"
        private const val notFoundInClientPassportNumber = "888888"
        private const val illegalPassportNumberLength = "10"
        private const val negativePassportNumber = "-111111"
    }

}
