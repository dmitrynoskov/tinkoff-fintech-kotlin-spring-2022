package ru.tinkoff.fintech.homework.homework07.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework07.model.Person

@Repository
interface UserRepository : JpaRepository<Person, String> {

    fun findByNameContaining(name: String, pageable: Pageable): Page<Person>

}
