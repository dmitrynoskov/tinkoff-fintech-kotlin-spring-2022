package ru.tinkoff.fintech.homework.homework06.controller

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.homework06.model.Person
import ru.tinkoff.fintech.homework.homework06.service.PersonService

@RestController
@RequestMapping("/")
class PersonController(private val personService: PersonService) {

    @PostMapping("/add")
    fun addPerson(@RequestBody passportNumber: String): Person =
        personService.addPerson(passportNumber)

    @GetMapping("/get/{passportNumber}")
    fun getPersonByPassportNumber(@PathVariable passportNumber: String): Person =
        personService.getPersonByPassportNumber(passportNumber)

    @GetMapping("/find")
    fun findPersonsByNameWithPagination(
        @RequestParam name: String,
        @RequestParam pageSize: Int,
        @RequestParam page: Int
    ): List<Person> =
        personService.findPersonsByNameWithPagination(name, pageSize, page)

}
