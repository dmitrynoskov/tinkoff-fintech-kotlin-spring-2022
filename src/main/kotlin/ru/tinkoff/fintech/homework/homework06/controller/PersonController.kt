package ru.tinkoff.fintech.homework.homework06.controller

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.homework06.model.Person
import ru.tinkoff.fintech.homework.homework06.service.PersonService

@RestController
@RequestMapping("/")
class PersonController(private val personService: PersonService) {

    @PostMapping("/add")
    fun addPerson(@RequestBody passportNumber: Int): Person =
        personService.addPerson(passportNumber)

    @GetMapping("/get/{passportNumber}")
    fun getPersonByPassportNumber(@PathVariable passportNumber: Int): Person =
        personService.getPersonByPassportNumber(passportNumber)

    @GetMapping("/find")
    fun findPersonsBySurnameWithPagination(
        @RequestParam surname: String,
        @RequestParam pageSize: Int,
        @RequestParam page: Int
    ): List<Person> =
        personService.findPersonsBySurnameWithPagination(surname, pageSize, page)

}
