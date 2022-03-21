package ru.tinkoff.fintech.tests.homework04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.tinkoff.fintech.homework.homework04.MyStackOnArray

class MyStackOnArrayTest {
    private var stack = MyStackOnArray<Int>()

    @BeforeEach
    fun beforeEach() {
        stack = MyStackOnArray()
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

    @Test
    fun testSelfExtension() {
        repeat(20) { stack.push(it) }
        val actual = IntArray(20) { stack.pop() }
        val expected = IntArray(20) { it }.reversedArray()
        assertArrayEquals(expected, actual)
    }

}
