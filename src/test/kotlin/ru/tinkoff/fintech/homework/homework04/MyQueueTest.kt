package ru.tinkoff.fintech.homework.homework04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.tinkoff.fintech.homework.homework04.MyQueue

class MyQueueTest {
    private var queue = MyQueue<Int>()

    @BeforeEach
    fun beforeEach() {
        queue = MyQueue()
    }

    @Test
    fun testElementException() {
        assertThrows<NoSuchElementException> {
            queue.element()
        }
    }

    @Test
    fun testRemoveException() {
        assertThrows<NoSuchElementException> {
            queue.remove()
        }
    }

    @Test
    fun testPeekReturnsNull() {
        assertNull(queue.peek())
    }

    @Test
    fun testPollReturnsNull() {
        assertNull(queue.poll())
    }

    @Test
    fun testOffer() {
        assertTrue(queue.offer(1))
    }

    @Test
    fun testElement() {
        queue.offer(1)
        queue.offer(2)
        queue.offer(3)
        val resultString = "" + queue.element() + queue.element() + queue.element() + queue.element()
        assertEquals("1111", resultString)
    }

    @Test
    fun testRemove() {
        queue.offer(1)
        queue.offer(2)
        queue.offer(3)
        val resultString = "" + queue.remove() + queue.remove() + queue.remove()
        assertEquals("123", resultString)
    }

    @Test
    fun testPeek() {
        queue.offer(1)
        queue.offer(2)
        queue.offer(3)
        val resultString = "" + queue.peek() + queue.peek() + queue.peek() + queue.peek()
        assertEquals("1111", resultString)
    }

    @Test
    fun testPoll() {
        queue.offer(1)
        queue.offer(2)
        queue.offer(3)
        val resultString = "" + queue.poll() + queue.poll() + queue.poll()
        assertEquals("123", resultString)
    }

}
