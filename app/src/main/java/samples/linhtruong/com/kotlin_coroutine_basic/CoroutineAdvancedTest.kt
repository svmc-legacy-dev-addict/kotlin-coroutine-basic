package samples.linhtruong.com.kotlin_coroutine_basic

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 9/20/19 - 21:47.
 * @organization VED
 */

class CoroutineAdvancedTest {
    private val scope = MainScope()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val test = CoroutineAdvancedTest()
        }
    }
}