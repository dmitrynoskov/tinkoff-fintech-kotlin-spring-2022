package ru.tinkoff.fintech.homework.homework09.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import ru.tinkoff.fintech.homework.homework09.model.Person

@Service
class PersonInformationClient(
    private val webClient: WebClient,
    @Value("\${person.information.address}") private val personInformationClientAddress: String
) {

    suspend fun getPerson(passportNumber: String): Person? {
        return webClient.get()
            .uri("$personInformationClientAddress$GET_PERSON_BY_PASSPORT", passportNumber)
            .retrieve()
            .awaitBodyOrNull()
    }

}

private const val GET_PERSON_BY_PASSPORT = "/passport?number={passportNumber}"
