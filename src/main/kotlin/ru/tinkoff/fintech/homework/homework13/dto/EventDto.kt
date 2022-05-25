package ru.tinkoff.fintech.homework.homework13.dto

import ru.tinkoff.fintech.homework.homework13.enum.Status
import ru.tinkoff.fintech.homework.homework13.enum.Type
import java.io.Serializable

data class EventDto(
    val id: Long,
    val type: Type,
    val body: String,
    var status: Status
) : Serializable
