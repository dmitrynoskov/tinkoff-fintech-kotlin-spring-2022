package ru.tinkoff.fintech.homework.homework05

data class Aircraft(
    val manufacturer: Manufacturer,
    val model: String,
    val fuselageType: Fuselage,
    val passengersCount: Int,
    val maxDistance: Int,
    val price: Int
)
