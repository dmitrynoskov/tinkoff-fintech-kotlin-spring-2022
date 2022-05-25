package ru.tinkoff.fintech.homework.homework13.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.homework13.dto.EventDto
import ru.tinkoff.fintech.homework.homework13.enum.Status
import ru.tinkoff.fintech.homework.homework13.enum.Type
import java.sql.ResultSet

@Repository
class JdbcEventDao(private val jdbcTemplate: JdbcTemplate) {

    fun getEventsWithStatus(status: Status): List<EventDto> {
        return jdbcTemplate.queryForStream(
            "select id, type, body, status from events where status = ?",
            PreparedStatementSetter {
                it.setString(1, status.name)
            },
            EventMapper
        ).toList()
    }

    fun updateStatus(eventId: Long, status: Status) {
        jdbcTemplate.update(
            "update events set status = ? where id = ?",
            status.name,
            eventId
        )
    }

    companion object EventMapper : RowMapper<EventDto> {
        override fun mapRow(rs: ResultSet, rowNum: Int): EventDto =
            EventDto(
                rs.getLong("id"),
                Type.valueOf(rs.getString("type")),
                rs.getString("body"),
                Status.valueOf(rs.getString("status"))
            )
    }
}
