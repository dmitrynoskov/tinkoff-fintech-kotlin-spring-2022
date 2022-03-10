package ru.tinkoff.fintech.tests.homework03

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import ru.tinkoff.fintech.homework.homework01.PassengersAircraft
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestPassengersAircraftLoadPeople {

    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val printStream = PrintStream(byteArrayOutputStream)
    private val originalOutput = System.out

    @BeforeAll
    fun beforeAll() {
        System.setOut(printStream)
    }

    @AfterAll
    fun afterAll() {
        System.setOut(originalOutput)
    }

    @AfterEach
    fun afterEach() {
        byteArrayOutputStream.reset()
    }

    @Test
    fun testNormalLoad() {
        val aircraft = PassengersAircraft(
            manufacturer = "Boeing",
            model = "737-800",
            maxPassengersCount = 170,
            fuselageType = PassengersAircraft.FuselageType.NarrowBody
        )
        aircraft.loadPeople(100)
        assertEquals("100 people was loaded\n", byteArrayOutputStream.toString())
        assertEquals(70, aircraft.availablePassengersCount)
    }

    @Test
    fun testOverload() {
        val aircraft = PassengersAircraft(
            manufacturer = "Boeing",
            model = "737-800",
            maxPassengersCount = 170,
            fuselageType = PassengersAircraft.FuselageType.NarrowBody
        )
        aircraft.loadPeople(200)
        assertEquals("Unable to load 200 people, it's 30 overbooked tickets\n", byteArrayOutputStream.toString())
        assertEquals(170, aircraft.availablePassengersCount)
    }

    @Test
    fun testZeroLoad() {
        val aircraft = PassengersAircraft(
            manufacturer = "Boeing",
            model = "737-800",
            maxPassengersCount = 170,
            fuselageType = PassengersAircraft.FuselageType.NarrowBody
        )
        aircraft.loadPeople(0)
        assertEquals("There are no people to load\n", byteArrayOutputStream.toString())
        assertEquals(170, aircraft.availablePassengersCount)
    }

    @Test
    fun testNegativeCountLoad() {
        val aircraft = PassengersAircraft(
            manufacturer = "Boeing",
            model = "737-800",
            maxPassengersCount = 170,
            fuselageType = PassengersAircraft.FuselageType.NarrowBody
        )
        val exception = assertThrows<IllegalArgumentException> {
            aircraft.loadPeople(-100)
        }
        assertEquals("People's count can't be negative!", exception.message)
        assertEquals(170, aircraft.availablePassengersCount)
    }
}
