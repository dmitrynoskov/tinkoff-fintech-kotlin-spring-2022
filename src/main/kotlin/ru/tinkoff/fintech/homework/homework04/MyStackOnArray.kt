package ru.tinkoff.fintech.homework.homework04

class MyStackOnArray<T> {
    private val INIT_SIZE = 16
    private val EXPANSION_FACTOR = 2
    private var array = arrayOfNulls<Any>(INIT_SIZE)
    private var currentPosition = 0

    fun pop(): T {
        val value = array[currentPosition] ?: throw NoSuchElementException()
        array[currentPosition--] = null
        return value as T
    }

    fun peek(): T? {
        return array[currentPosition] as T?
    }

    fun push(element: T): MyStackOnArray<T> {
        if (currentPosition == array.size - 1) {
            resize()
        }
        currentPosition++
        array[currentPosition] = element
        return this
    }

    private fun resize() {
        val newArray = arrayOfNulls<Any>(array.size * EXPANSION_FACTOR)
        System.arraycopy(array, 0, newArray, 0, array.size)
        array = newArray
    }

}
