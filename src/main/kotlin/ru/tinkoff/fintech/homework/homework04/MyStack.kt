package ru.tinkoff.fintech.homework.homework04

class MyStack<T> {
    private data class Node<T>(val value: T, var next: Node<T>? = null)

    private var top: Node<T>? = null

    fun pop(): T {
        val value = top?.value ?: throw NoSuchElementException()
        top = top?.next
        return value
    }

    fun peek(): T? {
        return top?.value
    }

    fun push(element: T): MyStack<T> {
        top = Node(element, top)
        return this
    }

}
