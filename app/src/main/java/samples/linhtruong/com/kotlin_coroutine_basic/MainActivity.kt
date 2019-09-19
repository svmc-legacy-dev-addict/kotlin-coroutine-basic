package samples.linhtruong.com.kotlin_coroutine_basic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        testCoroutineActivityLifecycle()
    }

    fun testCoroutineActivityLifecycle() = runBlocking {
        val activity = MainActivity()
        activity.doSomething()
        println("Launched coroutines")
        delay(500L)
        println("Destroying activity")
        activity.destroy()
        delay(1000L)
    }

    fun doSomething() {
        repeat(10) { i ->
            launch {
                delay((i + 1) * 200L)
                println("Coroutine $i is done")
            }
        }

    }

    fun destroy() {
        cancel() // cancel all jobs
    }
}
