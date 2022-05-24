package ru.tinkoff.fintech.homework.homework09.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.homework09.model.Person
import ru.tinkoff.fintech.homework.homework09.service.PersonService

@RestController
@RequestMapping("/")
class PersonController(private val personService: PersonService) {

    @PostMapping("/add")
    fun addPerson(@RequestBody passportNumber: String) =
        personService.addPerson(passportNumber)

    @GetMapping("/get/{passportNumber}")
    fun getPersonByPassportNumber(@PathVariable passportNumber: String): Person =
        personService.getPersonByPassportNumber(passportNumber)

}
