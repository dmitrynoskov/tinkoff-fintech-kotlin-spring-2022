package ru.tinkoff.fintech.homework.homework06.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework06.model.Person
import ru.tinkoff.fintech.homework.homework06.service.client.PersonInformationClient
import ru.tinkoff.fintech.homework.homework06.service.repository.PersonDatabase

@Service
class PersonService(
    private val personInformationClient: PersonInformationClient,
    private val personDatabase: PersonDatabase
) {

    fun addPerson(passportNumber: String): Person {
        validateNumber(passportNumber)
        val person = personInformationClient.getPerson(passportNumber)
        checkNotNull(person) { "Сервис не смог найти данные человека!" }
        personDatabase.savePerson(person)
        return person
    }

    fun getPersonByPassportNumber(passportNumber: String): Person {
        validateNumber(passportNumber)
        val person = personDatabase.getPersonByPassport(passportNumber)
        return requireNotNull(person) { "Человека с такими паспортными данными нет в базе!" }
    }

    fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> {
        require(page > 0 && pageSize > 0) { "Номер и размер странцы должны быть положительным числом!" }
        return personDatabase.findPersonsByNameWithPagination(name, pageSize, page)
    }

    private fun validateNumber(passportNumber: String) {
        require(passportNumber.length == 6 && passportNumber.toInt() >= 0) {
            "Номер паспорта должен содержать ровно 6 цифр!"
        }
    }

}
