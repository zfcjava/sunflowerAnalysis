package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class UiContinuationWrapper <T>(val continuation: Continuation<T>):Continuation<T>{
    //TODO zfc Coroutine到底和Context是什么关系
    override val context = continuation.context

    override fun resumeWith(result: Result<T>) {
        changeUiThread {
            continuation.resumeWith(result)
        }
    }

}