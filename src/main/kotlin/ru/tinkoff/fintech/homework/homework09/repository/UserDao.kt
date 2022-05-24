package ru.tinkoff.fintech.homework.homework09.repository

import ru.tinkoff.fintech.homework.homework09.model.Person

interface UserDao {

    fun savePerson(person: Person)

    fun getPersonByPassport(passportNumber: String): Person?

}
