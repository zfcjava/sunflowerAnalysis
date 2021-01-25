package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class BaseContinuation : Continuation<Unit>{

    //空的context实现
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {

    }

}