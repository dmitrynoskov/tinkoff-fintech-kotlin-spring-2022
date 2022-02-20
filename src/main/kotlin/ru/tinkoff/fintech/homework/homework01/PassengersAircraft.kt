package ru.tinkoff.fintech.homework.homework01

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

    override fun getInfo() {
        println(
            "An airliner $name is a $fuselageType aircraft produced by $manufacturer company"
                    + " and capable of carrying up to $maxPassengersCount people"
        )
    }

    fun loadPeople(count: Int) {
        if (count > availablePassengersCount) {
            println("Unable to load $count people, it's ${availablePassengersCount - count} overbooked tickets")
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