package ru.tinkoff.fintech.homework.homework01

abstract class Aircraft : Flyable {
    abstract val manufacturer: String
    abstract val model: String

    override val name: String
        get() = "$manufacturer $model"

    private fun takeOff(origin: String) {
        println("$name is taking off in $origin")
    }

    private fun landOn(destination: String) {
        println("$name is landing in $destination")
    }

    open fun flight(origin: String, destination: String) {
        takeOff(origin)
        fly()
        landOn(destination)
    }

    abstract fun getInfo()

}
