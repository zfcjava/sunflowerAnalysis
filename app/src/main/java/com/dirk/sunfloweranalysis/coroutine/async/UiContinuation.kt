package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class UiContinuationWrapper <T>(val continuation: Continuation<T>):Continuation<T>{
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<T>) {
        changeUiThread {
            continuation.resumeWith(result)
        }
    }

}