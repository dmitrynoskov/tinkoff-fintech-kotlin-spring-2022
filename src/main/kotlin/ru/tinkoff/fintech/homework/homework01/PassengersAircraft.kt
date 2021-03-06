package ru.tinkoff.fintech.homework.homework01

import java.lang.IllegalArgumentException

class PassengersAircraft(
    override val manufacturer: String,
    override val model: String,
    private val fuselageType: FuselageType,
    private val maxPassengersCount: Int
) : Aircraft() {

    private var readyToFlight: Boolean = false
    private var currentPassengersCount: Int = 0
    val availablePassengersCount: Int
        get() = maxPassengersCount - currentPassengersCount

    override fun flight(origin: String, destination: String) {
        if (readyToFlight) {
            super.flight(origin, destination)
            println("$currentPassengersCount passengers were carried")
            readyToFlight = false
            currentPassengersCount = 0
        } else {
            println("Unable to flight, some service works should be done")
        }
    }

    fun flight(destination: String) {
        flight(origin = "New York City", destination = destination) // Let's have base hub in New York
    }

    override fun getInfo() {
        println(
            "An airliner $name is a $fuselageType aircraft produced by $manufacturer company"
                    + " and capable of carrying up to $maxPassengersCount people"
        )
    }

    fun loadPeople(count: Int) {
        if (count < 0) {
            throw IllegalArgumentException("People's count can't be negative!")
        }
        if (count == 0) {
            println("There are no people to load")
        }
        else if (count > availablePassengersCount) {
            println("Unable to load $count people, it's ${count - availablePassengersCount} overbooked tickets")
        } else {
            currentPassengersCount += count
            println("$count people was loaded")
        }
    }

    fun prepareForFlight() {
        cleanUpCabin()
        loadFood()
        readyToFlight = true
    }

    private fun loadFood() {
        println("Loading food")
    }

    private fun cleanUpCabin() {
        println("Cleaning up cabin")
    }

    enum class FuselageType {
        NarrowBody, WideBody
    }

}
