package ru.tinkoff.fintech.homework.homework03

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import ru.tinkoff.fintech.homework.homework01.CargoAirline
import ru.tinkoff.fintech.homework.homework01.CargoAircraft
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCargoAirline {

    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val printStream = PrintStream(byteArrayOutputStream)
    private val originalOutput = System.out
    private val firstAircraft = mockk<CargoAircraft>()
    private val secondAircraft = mockk<CargoAircraft>()

    @BeforeAll
    fun beforeAll() {
        System.setOut(printStream)
    }

    @AfterAll
    fun afterAll() {
        System.setOut(originalOutput)
    }

    @BeforeEach
    fun beforeEach() {
        every { firstAircraft.availableWeight } returns 500
        every { secondAircraft.availableWeight } returns 500
        every { firstAircraft.loadCargo(any()) } answers { println("first loading") }
        every { firstAircraft.flight(any(), any()) } answers { println("first flying") }
        every { secondAircraft.loadCargo(any()) } answers { println("second loading") }
        every { secondAircraft.flight(any(), any()) } answers { println("second flying") }
    }

    @AfterEach
    fun afterEach() {
        byteArrayOutputStream.reset()
        clearAllMocks()
    }

    @Test
    fun testEmptyAirline() {
        val airline = CargoAirline("Alaska")
        airline.transferCargo("Boston", "Chicago", 500)
        assertEquals("Airline Alaska can handle only 0 tons of cargo\n", byteArrayOutputStream.toString())
    }

    @Test
    fun testNegativeWeight() {
        val airline = CargoAirline("Alaska", firstAircraft)
        val exception = assertThrows<IllegalArgumentException> {
            airline.transferCargo("Boston", "Chicago", -500)
        }
        assertEquals("Weight can't be negative!", exception.message)
        verify(exactly = 0) { firstAircraft.availableWeight }
        verify(exactly = 0) { firstAircraft.loadCargo(any()) }
        verify(exactly = 0) { firstAircraft.flight(any(), any()) }
    }

    @Test
    fun testZeroWeight() {
        val airline = CargoAirline("Alaska", firstAircraft)
        airline.transferCargo("Boston", "Chicago", 0)
        assertEquals("There is nothing to transfer\n", byteArrayOutputStream.toString())
        verify(exactly = 0) { firstAircraft.availableWeight }
        verify(exactly = 0) { firstAircraft.loadCargo(any()) }
        verify(exactly = 0) { firstAircraft.flight(any(), any()) }
    }

    @Test
    fun testNormalTransfer() {
        val airline = CargoAirline("Alaska", firstAircraft)
        airline.transferCargo("Boston", "Chicago", 100)
        assertEquals("first loading\nfirst flying\n", byteArrayOutputStream.toString())
        verify(exactly = 2) { firstAircraft.availableWeight }
        verify(exactly = 1) { firstAircraft.loadCargo(100) }
        verify(exactly = 1) { firstAircraft.flight("Boston", "Chicago") }
    }

    @Test
    fun testOverload() {
        val airline = CargoAirline("Alaska", firstAircraft)
        airline.transferCargo("Boston", "Chicago", 1000)
        assertEquals("Airline Alaska can handle only 500 tons of cargo\n", byteArrayOutputStream.toString())
        verify(exactly = 2) { firstAircraft.availableWeight }
        verify(exactly = 0) { firstAircraft.loadCargo(any()) }
        verify(exactly = 0) { firstAircraft.flight(any(), any()) }
    }

    @Test
    fun testSharingBetweenTwoPlanes() {
        val airline = CargoAirline("Alaska", firstAircraft, secondAircraft)
        airline.transferCargo("Boston", "Chicago", 900)
        assertEquals(
            "first loading\nfirst flying\nsecond loading\nsecond flying\n", byteArrayOutputStream.toString()
        )
        verify(exactly = 2) { firstAircraft.availableWeight }
        verify(exactly = 1) { firstAircraft.loadCargo(500) }
        verify(exactly = 1) { firstAircraft.flight("Boston", "Chicago") }
        verify(exactly = 2) { secondAircraft.availableWeight }
        verify(exactly = 1) { secondAircraft.loadCargo(400) }
        verify(exactly = 1) { secondAircraft.flight("Boston", "Chicago") }
    }

}
