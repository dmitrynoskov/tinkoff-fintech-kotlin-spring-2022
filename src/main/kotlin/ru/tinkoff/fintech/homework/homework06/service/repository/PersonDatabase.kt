package ru.tinkoff.fintech.homework.homework06.service.repository

import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework06.model.Person
import java.util.concurrent.ConcurrentHashMap

@Repository
class PersonDatabase {
    private val persons: ConcurrentHashMap<String, Person> = ConcurrentHashMap()

    fun savePerson(person: Person) {
        persons[person.passportNumber] = person;
    }

    fun getPersonByPassport(passportNumber: String): Person? {
        return persons[passportNumber];
    }

    fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> =
        persons.asSequence()
            .map { it.value }
            .filter { it.name == name }
            .sortedBy { it.surname }
            .drop(pageSize * (page - 1))
            .take(pageSize)
            .toList()

}
