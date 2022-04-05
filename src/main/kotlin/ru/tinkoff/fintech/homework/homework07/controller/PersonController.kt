package ru.tinkoff.fintech.homework.homework07.controller

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.homework07.model.Person
import ru.tinkoff.fintech.homework.homework07.service.PersonService

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
        @RequestParam pageSize: Int = 10,
        @RequestParam page: Int = 0
    ): List<Person> =
        personService.findPersonsByNameWithPagination(name, pageSize, page)

}
