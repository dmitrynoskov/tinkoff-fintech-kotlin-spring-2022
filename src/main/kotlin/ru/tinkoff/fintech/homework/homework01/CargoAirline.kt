package ru.tinkoff.fintech.homework.homework01

import java.lang.IllegalArgumentException
import kotlin.math.min

class CargoAirline(private val name: String, vararg airplanes: CargoAircraft) {
    private val cargoFleet = airplanes.asList()

    fun transferCargo(origin: String, destination: String, quantity: Int) {
        if (quantity < 0) {
            throw IllegalArgumentException("Weight can't be negative!")
        }
        var weight = quantity
        if (weight == 0) {
            println("There is nothing to transfer")
        } else if (weight <= cargoFleet.sumOf { it.availableWeight }) {
            for (plane in cargoFleet) {
                val loadedCargo = min(weight, plane.availableWeight)
                plane.loadCargo(loadedCargo)
                plane.flight(origin, destination)
                weight -= loadedCargo
            }
        } else {
            println("Airline $name can handle only ${cargoFleet.sumOf { it.availableWeight }} tons of cargo")
        }
    }
}
