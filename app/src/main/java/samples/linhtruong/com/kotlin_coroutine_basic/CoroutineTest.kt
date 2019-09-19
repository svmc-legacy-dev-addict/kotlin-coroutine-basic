package samples.linhtruong.com.kotlin_coroutine_basic

import kotlinx.coroutines.*


/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 9/19/19 - 22:28.
 * @organization VED
 */

class CoroutineTest {

    private lateinit var customScope: CoroutineScope

    companion object {
        private const val TAG = "coroutine-test"
        private const val TIME_1S = 1000L
        private const val TIME_2S = 1000L
        private const val TIME_500MS = 500L
        private const val TIME_200MS = 200L
        private const val TIME_100MS = 100L

        @JvmStatic
        fun main(args: Array<String>) {
            val test = CoroutineTest()


            // use runBlocking to for test suspending functions
//        test.test1()
//        test.test2()

            // join() to wait job to complete
//        test.testJob()

            // create custom scope instead
//        test.testScope()

            // cancel the job
//            test.testCancel()
//            test.testUncancelable()

            // sync - sequential call
//            test.testChainCall()

            // async
//            test.testAsyncCall()

            // dispatcher - CoroutineContext
            test.testDispatchers()

        }
    }

    private fun logd(msg: String) {
        println(msg)
    }


    private fun testCustomScope() {
        customScope = GlobalScope
        customScope = MainScope()
        customScope = CoroutineScope(Dispatchers.Default)
    }

    private fun testDispatchers() = runBlocking<Unit>
    {
        launch() {
            // context of the parent, main runBlocking coroutine
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
//        launch(Dispatchers.Main) {
//            // context of the parent, main runBlocking coroutine
//            println("Main      : I'm working in thread ${Thread.currentThread().name}")
//        }
        launch(Dispatchers.Unconfined) {
            // not confined -- will work with main thread
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        // same as GlobalScope.launch {  }
        launch(Dispatchers.Default) {
            // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            // will get its own new thread
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    private fun testChainCall() = runBlocking {
        val t = System.currentTimeMillis()
        launch {
            val job1 = jobRun(1)
            val job2 = jobRun(2)
            val job3 = jobRun(3)
            val result = job1 + job2 + job3
            print("result: $result in: ${System.currentTimeMillis() - t}")
        }
    }

    private fun testAsyncCall() = runBlocking {
        val t = System.currentTimeMillis()
        launch {
            //            val job1 = async { jobRun(1) } // started
            val job1 = async(start = CoroutineStart.LAZY) { jobRun(1) } // lazy
            val job2 = async { jobRun(2) }
            val job3 = async { jobRun(3) }

            job1.start()
            val result = job1.await() + job2.await() + job3.await()
            print("result: $result in: ${System.currentTimeMillis() - t}")
        }
    }

    private suspend fun jobRun(i: Int): Int {
        logd("job$i started ")
        delay(500L)
        logd("job$i ended ")
        return i
    }

    private fun testCancel() = runBlocking {
        val job = launch {
            repeat(1000) {
                logd("job: counting $it")
                delay(300L)
            }
        }

        delay(1000L)
        logd("main: tired of waiting...")
        job.cancel()
        logd("main: quited ")
    }

    private fun testUncancelable() = runBlocking {
        val job = withContext(NonCancellable) {
            launch {
                repeat(5) {
                    logd("job: counting $it")
                    delay(300L)
                }
            }
        }

        delay(1000L)
        logd("main: tired of waiting...")
        job.cancel()
        logd("main: quited ")
    }


    private fun test1() {
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(TIME_1S) // non-blocking delay for 1 second (default time unit is ms)
            logd("World!") // print after delay
        }
        logd("Hello,") // main thread continues while coroutine is delayed
        Thread.sleep(TIME_2S) // block main thread for 2 seconds to keep JVM alive
    }


    private fun test2() = runBlocking {
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(TIME_1S)
            logd("World!")
        }
        logd("Hello,") // main coroutine continues here immediately
        delay(TIME_2S)      // delaying for 2 seconds to keep JVM alive
    }

    private fun testJob() = runBlocking {
        val job = GlobalScope.launch {
            delay(TIME_1S)
            logd("World!")
        }

        logd("Hello,")
        job.join()
    }

    private fun testScope() = runBlocking {
        launch {
            delay(TIME_200MS)
            logd("Task from runBlocking scope")
        }

        coroutineScope {
            launch {
                extractLaunchFunc(TIME_500MS)
                logd("Task from nested launch")
            }

            delay(TIME_100MS)
            logd("Task from custom coroutine scope")
        }

        logd("Coroutine scope is over!")
    }

    private suspend fun extractLaunchFunc(time: Long) {
        delay(time)
    }
}