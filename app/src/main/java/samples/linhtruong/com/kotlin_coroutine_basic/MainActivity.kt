package samples.linhtruong.com.kotlin_coroutine_basic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "coroutine-test"
        private const val TIME_1S = 1000L
        private const val TIME_2S = 1000L
        private const val TIME_500MS = 500L
        private const val TIME_200MS = 200L
        private const val TIME_100MS = 100L
    }

    private fun logd(msg: String) {
        Log.d(TAG, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        // use runBlocking to for test suspending functions
//        test1()
//        test2()

        // join() to wait job to complete
//        testJob()

        // create custom scope instead
        testScope()
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
