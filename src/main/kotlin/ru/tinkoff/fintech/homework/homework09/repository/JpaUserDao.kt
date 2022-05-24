package ru.tinkoff.fintech.homework.homework09.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework09.model.Person

@Primary
@Repository
class JpaUserDao(private val userRepository: UserRepository) : UserDao {
    override fun savePerson(person: Person) {
        userRepository.save(person)
    }

    override fun getPersonByPassport(passportNumber: String): Person? {
        return userRepository.findByIdOrNull(passportNumber)
    }

}
