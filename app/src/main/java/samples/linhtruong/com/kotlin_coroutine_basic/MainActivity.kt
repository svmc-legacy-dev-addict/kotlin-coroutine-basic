package samples.linhtruong.com.kotlin_coroutine_basic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private lateinit var cont: Continuation<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val button = findViewById<Button>(R.id.testContinuation)
        button.setOnClickListener {
            GlobalScope.launch {
                try {
                    val result = testContinuation()
                    Snackbar.make(button, "result after onActivityResult is: $result", Snackbar.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Snackbar.make(button, "result after onActivityResult is: ${e.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun testContinuation(): Int = suspendCoroutine<Int> {
        cont = it
        startActivityForResult(Intent(this, TestActivity::class.java), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            cont.resume(999)
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            cont.resumeWithException(Exception("TEST"))
        }
    }
}
