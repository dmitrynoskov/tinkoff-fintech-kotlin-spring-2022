package ru.tinkoff.fintech.homework.homework01

class CargoAircraft(override val manufacturer: String, override val model: String, private val maxCargoWeight: Int) :
    Aircraft() {

    private var currentWeight: Int = 0
    val availableWeight: Int
        get() = maxCargoWeight - currentWeight

    override fun flight(origin: String, destination: String) {
        super.flight(origin, destination)
        println("$currentWeight tons of cargo was transported")
        currentWeight = 0
    }

    override fun getInfo() {
        println(
            "An airliner $name is cargo aircraft produced by $manufacturer company"
                    + " and capable of transferring up to $maxCargoWeight people"
        )
    }

    fun loadCargo(weight: Int) {
        if (weight > availableWeight) {
            println("Unable to load $weight tons, it's ${availableWeight - weight} tons of overload")
        } else {
            currentWeight += weight
            println("$weight tons was loaded")
        }
    }

}
