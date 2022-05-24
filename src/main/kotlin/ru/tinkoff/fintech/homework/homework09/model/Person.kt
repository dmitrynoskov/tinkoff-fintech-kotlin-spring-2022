package ru.tinkoff.fintech.homework.homework09.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "persons")
data class Person(
    @Column(name = "first_name") val name: String,

    @Column(name = "last_name") val surname: String,

    @Column(name = "birth_date") val birthDate: LocalDate,

    @Id
    @Column(name = "passport_number") val passportNumber: String


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this.javaClass != other.javaClass) {
            return false
        }
        other as Person
        return passportNumber == other.passportNumber
    }

    override fun hashCode(): Int {
        return 17
    }
}
