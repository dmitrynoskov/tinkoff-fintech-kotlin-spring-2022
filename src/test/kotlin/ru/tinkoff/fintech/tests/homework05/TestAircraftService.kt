package ru.tinkoff.fintech.tests.homework05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.tinkoff.fintech.homework.homework05.*

class TestAircraftService {
    private val fleet = listOf(
        Aircraft(Manufacturer.Boeing, "737-800", Fuselage.NarrowBody, 180, 100, 45),
        Aircraft(Manufacturer.Airbus, "A350-900", Fuselage.WideBody, 300, 240, 299),
        Aircraft(Manufacturer.Boeing, "737-900", Fuselage.NarrowBody, 190, 90, 48),
        Aircraft(Manufacturer.Airbus, "A330-900", Fuselage.WideBody, 240, 200, 200),
        Aircraft(Manufacturer.Boeing, "787-900", Fuselage.WideBody, 220, 210, 250)
    )

    @Test
    fun testConvertingToDescription() {
        val expected = listOf(
            AircraftDescription("Boeing", "737-800", "NarrowBody", 180, 100 * 2, 45 * 1000),
            AircraftDescription("Boeing", "737-900", "NarrowBody", 190, 90 * 2, 48 * 1000),
            AircraftDescription("Airbus", "A330-900", "WideBody", 240, 200 * 2, 200 * 1000),
            AircraftDescription("Boeing", "787-900", "WideBody", 220, 210 * 2, 250 * 1000),
            AircraftDescription("Airbus", "A350-900", "WideBody", 300, 240 * 2, 299 * 1000)
        )
        val actual = AircraftService.convertToDescription(fleet)
        assertEquals(expected, actual)

    }

    @Test
    fun testGroupingByFuselageType() {
        val expected = mapOf(
            Fuselage.NarrowBody to listOf(
                Aircraft(Manufacturer.Boeing, "737-800", Fuselage.NarrowBody, 180, 100, 45),
                Aircraft(Manufacturer.Boeing, "737-900", Fuselage.NarrowBody, 190, 90, 48)
            ),
            Fuselage.WideBody to listOf(
                Aircraft(Manufacturer.Airbus, "A350-900", Fuselage.WideBody, 300, 240, 299),
                Aircraft(Manufacturer.Airbus, "A330-900", Fuselage.WideBody, 240, 200, 200),
                Aircraft(Manufacturer.Boeing, "787-900", Fuselage.WideBody, 220, 210, 250)
            )
        )
        val actual = AircraftService.groupByFuselageType(fleet)
        assertEquals(expected, actual)
    }

    @Test
    fun testFindingAircraftsWithGivenPredicate() {
        val predicate: (Aircraft) -> Boolean = { it.passengersCount >= 190 }
        val expected = listOf(
            "Airbus A350-900",
            "Boeing 737-900",
            "Airbus A330-900"
        )
        val actual = AircraftService.findAircraftsWithGivenPredicate(fleet, predicate)
        assertEquals(expected, actual)
    }

}
