package ru.tinkoff.fintech.homework.homework07.repository

import ru.tinkoff.fintech.homework.homework07.model.Person

interface UserDao {

    fun savePerson(person: Person)

    fun getPersonByPassport(passportNumber: String): Person?

    fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person>

}
