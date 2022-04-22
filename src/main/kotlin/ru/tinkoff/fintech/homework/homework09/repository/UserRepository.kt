package ru.tinkoff.fintech.homework.homework09.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework09.model.Person

@Repository
interface UserRepository : JpaRepository<Person, String> {}
