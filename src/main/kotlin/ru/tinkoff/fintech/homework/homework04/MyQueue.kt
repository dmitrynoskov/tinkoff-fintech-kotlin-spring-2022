package ru.tinkoff.fintech.homework.homework04

class MyQueue<T> {
    private data class Node<T>(val value: T, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    fun element(): T {
        return head?.value ?: throw NoSuchElementException()
    }

    fun peek(): T? {
        return head?.value
    }

    fun remove(): T {
        val value = head?.value ?: throw NoSuchElementException()
        head = head?.next
        if (head == null) {
            tail = null
        }
        return value
    }

    fun poll(): T? {
        val value = head?.value
        head = head?.next
        if (head == null) {
            tail = null
        }
        return value
    }

    fun offer(obj: T): Boolean {
        val newNode = Node(obj)
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = tail?.next
        }
        return true
    }

}
