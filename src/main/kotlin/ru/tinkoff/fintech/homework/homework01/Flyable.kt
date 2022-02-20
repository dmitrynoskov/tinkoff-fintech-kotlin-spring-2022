package ru.tinkoff.fintech.homework.homework01

interface Flyable {

    val name: String

    fun fly() {
        println("$name is flying")
    }
}