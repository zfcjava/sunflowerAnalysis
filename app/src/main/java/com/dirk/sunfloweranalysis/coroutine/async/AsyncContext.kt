package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class AsyncContext: AbstractCoroutineContextElement(ContinuationInterceptor),ContinuationInterceptor {

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {

        return UiContinuationWrapper(continuation.context.fold(continuation){
            continuation, element ->
            if(element != this && element is ContinuationInterceptor){
                element.interceptContinuation(continuation)
            } else continuation
        })
    }
}