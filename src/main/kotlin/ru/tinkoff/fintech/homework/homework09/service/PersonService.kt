package ru.tinkoff.fintech.homework.homework09.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework09.model.Person
import ru.tinkoff.fintech.homework.homework09.service.client.PersonInformationClient
import ru.tinkoff.fintech.homework.homework09.repository.UserDao

@Service
class PersonService(
    private val personInformationClient: PersonInformationClient,
    private val userDao: UserDao
) {

    fun addPerson(passportNumber: String) {
        CoroutineScope(Dispatchers.Default).launch {
            validateNumber(passportNumber)
            val person = personInformationClient.getPerson(passportNumber)
            checkNotNull(person) { "Сервис не смог найти данные человека с номером паспорта $passportNumber!" }
            withContext(Dispatchers.IO) {
                userDao.savePerson(person)
            }
        }
    }

    fun getPersonByPassportNumber(passportNumber: String): Person {
        validateNumber(passportNumber)
        val person = userDao.getPersonByPassport(passportNumber)
        return requireNotNull(person) { "Человека с номером паспорта $passportNumber нет в базе!" }
    }

    private fun validateNumber(passportNumber: String) {
        require(passportNumber.length == 6 && passportNumber.toInt() >= 0) {
            "Номер паспорта должен содержать ровно 6 цифр!"
        }
    }

}
