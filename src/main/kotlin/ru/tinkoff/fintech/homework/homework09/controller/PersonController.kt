package ru.tinkoff.fintech.homework.homework09.controller

import org.springframework.web.bind.annotation.*
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
