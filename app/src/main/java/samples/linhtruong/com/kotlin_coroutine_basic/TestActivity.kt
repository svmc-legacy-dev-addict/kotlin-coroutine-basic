package samples.linhtruong.com.kotlin_coroutine_basic

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class TestActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_test)

        findViewById<Button>(R.id.backWithResult).setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        findViewById<Button>(R.id.backWithResultException).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
