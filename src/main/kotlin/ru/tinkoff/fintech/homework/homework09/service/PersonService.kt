package ru.tinkoff.fintech.homework.homework09.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework09.model.Person
import ru.tinkoff.fintech.homework.homework09.repository.UserDao
import ru.tinkoff.fintech.homework.homework09.service.client.PersonInformationClient

@Service
class PersonService(
    private val personInformationClient: PersonInformationClient,
    private val userDao: UserDao
) {

    fun addPerson(passportNumber: String) {
        validateNumber(passportNumber)
        CoroutineScope(Dispatchers.Default).launch {
            val person = personInformationClient.getPerson(passportNumber)
            withContext(Dispatchers.IO) {
                person?.let { userDao.savePerson(it) }
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
