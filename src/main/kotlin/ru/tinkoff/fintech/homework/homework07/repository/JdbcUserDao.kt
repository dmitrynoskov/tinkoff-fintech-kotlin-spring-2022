package ru.tinkoff.fintech.homework.homework07.repository

import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.homework07.model.Person
import java.sql.ResultSet

@Primary
@Service
class JdbcUserDao(val jdbcTemplate: JdbcTemplate) : UserDao {

    override fun savePerson(person: Person) {
        jdbcTemplate.update(
            "insert into persons(first_name, last_name, birth_date, passport_number) values (?, ?, ?, ?)",
            person.name,
            person.surname,
            person.birthDate,
            person.passportNumber
        )
        //TODO("сделать апдейт, если человек уже есть")
    }

    override fun getPersonByPassport(passportNumber: String): Person? {
        return jdbcTemplate.queryForStream(
            "select * from persons where passport_number = $passportNumber",
            PersonMapper
        ).findAny().orElse(null)
    }

    override fun findPersonsByNameWithPagination(name: String, pageSize: Int, page: Int): List<Person> {
        return jdbcTemplate.query(
            "select * from persons where first_name = '$name' order by last_name limit $pageSize offset ${pageSize * (page - 1)}",
            PersonMapper
        )
    }

    private companion object PersonMapper : RowMapper<Person> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Person =
            Person(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getString("passport_number")
            )
    }
}
