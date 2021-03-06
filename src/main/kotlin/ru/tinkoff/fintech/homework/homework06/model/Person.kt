package ru.tinkoff.fintech.homework.homework06.model

import java.time.LocalDate

data class Person(
    val name: String,
    val surname: String,
    val birthDate: LocalDate,
    val passportNumber: String
)
