package ru.tinkoff.fintech.homework.homework06.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.homework06.model.Person

@Service
class PersonInformationClient(
    private val restTemplate: RestTemplate,
    @Value("\${person.information.address}") private val personInformationClientAddress: String
) {

    fun getPerson(passportNumber: Int): Person? = try {
        restTemplate.getForObject<Person>("$personInformationClientAddress$GET_PERSON_BY_PASSPORT")
    } catch (e: HttpClientErrorException.NotFound) {
        null
    }
}

private const val GET_PERSON_BY_PASSPORT = "/passport?number={passportNumber}"
