package ru.tinkoff.fintech.homework.homework07.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework07.model.Person

@Primary
@Repository
class JpaUserDao(private val userRepository: UserRepository) : UserDao {
    override fun savePerson(person: Person) {
        userRepository.save(person)
    }

    override fun getPersonByPassport(passportNumber: String): Person? {
        return userRepository.findByIdOrNull(passportNumber)
    }

    override fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> {
        val pageRequest = PageRequest.of(page, pageSize, Sort.by("surname"))
        return userRepository.findByNameContaining(name, pageRequest).content
    }
}
