package ru.tinkoff.fintech.homework.homework04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.tinkoff.fintech.homework.homework04.MyStack

class MyStackTest {
    private var stack = MyStack<Int>()

    @BeforeEach
    fun beforeEach() {
        stack = MyStack()
    }

    @Test
    fun testPopException() {
        assertThrows<NoSuchElementException> {
            stack.pop()
        }
    }

    @Test
    fun testPeekReturnsNull() {
        assertNull(stack.peek())
    }

    @Test
    fun testPop() {
        stack.push(1).push(2).push(3)
        val resultString = "" + stack.pop() + stack.pop() + stack.pop()
        assertEquals("321", resultString)
    }

    @Test
    fun testPeek() {
        stack.push(1).push(2).push(3)
        val resultString = "" + stack.peek() + stack.peek() + stack.peek() + stack.peek()
        assertEquals("3333", resultString)
    }

}
