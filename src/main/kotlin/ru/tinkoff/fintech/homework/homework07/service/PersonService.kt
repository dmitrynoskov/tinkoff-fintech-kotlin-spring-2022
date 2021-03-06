package ru.tinkoff.fintech.homework.homework07.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework07.model.Person
import ru.tinkoff.fintech.homework.homework07.service.client.PersonInformationClient
import ru.tinkoff.fintech.homework.homework07.repository.UserDao

@Service
class PersonService(
    private val personInformationClient: PersonInformationClient,
    private val userDao: UserDao
) {

    fun addPerson(passportNumber: String): Person {
        validateNumber(passportNumber)
        val person = personInformationClient.getPerson(passportNumber)
        checkNotNull(person) { "Сервис не смог найти данные человека с номером паспорта $passportNumber!" }
        userDao.savePerson(person)
        return person
    }

    fun getPersonByPassportNumber(passportNumber: String): Person {
        validateNumber(passportNumber)
        val person = userDao.getPersonByPassport(passportNumber)
        return requireNotNull(person) { "Человека с номером паспорта $passportNumber нет в базе!" }
    }

    fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> {
        require(page >= 0) { "Номер странцы не может быть отрицательным!" }
        require(pageSize > 0) { "Размер странцы должен быть больше нуля!" }
        return userDao.findPersonsByNameWithPagination(name, pageSize, page)
    }

    private fun validateNumber(passportNumber: String) {
        require(passportNumber.length == 6 && passportNumber.toInt() >= 0) {
            "Номер паспорта должен содержать ровно 6 цифр!"
        }
    }

}
