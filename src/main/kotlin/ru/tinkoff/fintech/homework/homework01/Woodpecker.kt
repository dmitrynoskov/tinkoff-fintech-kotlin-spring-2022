package ru.tinkoff.fintech.homework.homework01

class Woodpecker(override val name: String) : Flyable {
    fun knock() {
        println("$name is knocking...")
    }
}
