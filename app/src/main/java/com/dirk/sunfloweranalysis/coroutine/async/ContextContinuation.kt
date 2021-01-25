package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ContextContinuation(override val context:CoroutineContext = EmptyCoroutineContext) : Continuation<Unit>{

    override fun resumeWith(result: Result<Unit>) {

    }

}