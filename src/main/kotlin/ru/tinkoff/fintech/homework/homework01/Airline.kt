package ru.tinkoff.fintech.homework.homework01

import kotlin.math.min

class Airline(private val name: String, vararg airplanes: Aircraft) {
    private val passengersFleet = airplanes.asList().filterIsInstance<PassengersAircraft>()
    private val cargoFleet = airplanes.asList().filterIsInstance<CargoAircraft>()

    fun transferPeople(origin: String, destination: String, quantity: Int) {
        var count = quantity
        if (count <= passengersFleet.sumOf { it.availablePassengersCount }) {
            for (plane in passengersFleet) {
                if (count <= 0) {
                    return
                } else {
                    plane.prepareForFlight()
                    val loadedPeople = min(count, plane.availablePassengersCount)
                    plane.loadPeople(loadedPeople)
                    plane.flight(origin, destination)
                    count -= loadedPeople
                }
            }

        } else {
            println("Airline $name can handle only ${passengersFleet.sumOf { it.availablePassengersCount }} passengers")
        }
    }

    fun transferCargo(origin: String, destination: String, quantity: Int) {
        var weight = quantity
        if (weight <= cargoFleet.sumOf { it.availableWeight }) {
            for (plane in cargoFleet) {
                if (weight <= 0) {
                    return
                } else {
                    val loadedCargo = min(weight, plane.availableWeight)
                    plane.loadCargo(loadedCargo)
                    plane.flight(origin, destination)
                    weight -= loadedCargo
                }
            }

        } else {
            println("Airline $name can handle only ${cargoFleet.sumOf { it.availableWeight }} tons of cargo")
        }
    }
}
