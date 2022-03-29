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

    fun addPerson(passportNumber: Int): Person {
        require(passportNumber > 0 && passportNumber.toString().length == 6) { "Номер паспорта должен содержать ровно 6 цифр!" }
        val person = personInformationClient.getPerson(passportNumber)
        checkNotNull(person) { "Сервис не смог найти данные человека!" }
        personDatabase.savePerson(person)
        return person
    }

    fun getPersonByPassportNumber(passportNumber: Int): Person {
        val person = personDatabase.getPersonByPassport(passportNumber)
        return requireNotNull(person) { "Человека с такими паспортными данными нет в базе!" }
    }

    fun findPersonsBySurnameWithPagination(surname: String, pageSize: Int, page: Int): List<Person> {
        require(page > 0 && pageSize > 0) { "Номер и размер странцы должны быть положительным числом!" }
        return personDatabase.findPersonsBySurnameWithPagination(surname, pageSize, page)
    }

}
