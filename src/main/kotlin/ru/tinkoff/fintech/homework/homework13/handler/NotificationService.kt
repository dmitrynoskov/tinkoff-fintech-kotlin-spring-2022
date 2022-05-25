package ru.tinkoff.fintech.homework.homework13.handler

interface NotificationService {

    fun send(message: String) {
        println("sending from ${this::class.java.simpleName}")
        println(message)
    }

}
