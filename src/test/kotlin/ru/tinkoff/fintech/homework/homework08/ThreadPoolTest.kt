package ru.tinkoff.fintech.homework.homework08

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import java.lang.Thread.sleep
import java.util.concurrent.ConcurrentHashMap

class ThreadPoolTest : FeatureSpec() {

    init {

        feature("test exceptions") {
            scenario("illegal thread's count") {
                shouldThrow<IllegalArgumentException> { ThreadPool(0) }
                shouldThrow<IllegalArgumentException> { ThreadPool(6) }
            }
            scenario("runnable is null") {
                shouldThrow<IllegalArgumentException> { ThreadPool(1).execute(null) }
            }
            scenario("adding tasks to stopped ThreadPool") {
                shouldThrow<IllegalStateException> {
                    val pool = ThreadPool(1)
                    pool.shutdown()
                    pool.execute { }
                }
            }
        }

        feature("normal work") {
            val pool = ThreadPool(5)
            val map = ConcurrentHashMap<Int, Int>()
            repeat(5) {
                pool.execute {
                    map[it] = it
                    sleep(10000)
                }
            }
            pool.shutdown()
            map shouldBe expectedMap
        }

    }

    companion object {
        private val expectedMap = ConcurrentHashMap(mapOf(0 to 0, 1 to 1, 2 to 2, 3 to 3, 4 to 4))
    }

}
