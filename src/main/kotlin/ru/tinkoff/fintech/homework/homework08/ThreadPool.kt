package ru.tinkoff.fintech.homework.homework08

import io.swagger.v3.oas.models.security.SecurityScheme.In
import java.lang.Thread.currentThread
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(threadQuantity: Int) : Executor {

    private val taskQueue: Queue<Runnable> = LinkedBlockingQueue()
    private val threadList: MutableList<WorkerThread> = mutableListOf()

    @Volatile
    private var isRunning = true

    init {
        require(threadQuantity in 1..MAX_THREADS) {
            "Thread quantity should be greater then 0 and less then $MAX_THREADS!"
        }
        repeat(threadQuantity) {
            threadList.add(WorkerThread())
            threadList.last().start()
        }
    }

    override fun execute(command: Runnable?) {
        requireNotNull(command) { "Runnable task can not be null!" }
        check(isRunning) { "Thread pool is stopped and can not execute new tasks!" }
        synchronized(taskQueue) {
            taskQueue.add(command)
            (taskQueue as Object).notify()
        }
    }

    fun shutdown() {
        isRunning = false
        synchronized(taskQueue) {
            (taskQueue as Object).notifyAll()
        }
    }

    private inner class WorkerThread : Thread() {

        override fun run() {
            var task: Runnable?

            while (isRunning || taskQueue.isNotEmpty()) {
                synchronized(taskQueue) {
                    while (isRunning && taskQueue.isEmpty()) {
                        (taskQueue as Object).wait()
                    }
                    task = taskQueue.poll()
                }
                task?.run()
            }
        }

    }

    companion object {
        const val MAX_THREADS = 5
    }

}

fun main() {

    val map: ConcurrentHashMap<Int, Int> = ConcurrentHashMap()
    val pool = ThreadPool(2)
    repeat(5) {
        pool.execute {
            println("started at ${currentThread().name}")
            sleep(1000)
            map[it] = it
            println("stopped at ${currentThread().name}")
        }
    }
    println("main thread continues his work")
    pool.shutdown()
    println("current map size is ${map.size}")
    sleep(5000)
    println("map size after 5 sec is ${map.size}")
}
