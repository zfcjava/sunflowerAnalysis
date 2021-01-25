package com.dirk.sunfloweranalysis.coroutine.basic

import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

fun call(){
    startMyCouroutine {
        val result = loadImage("123")
        //接下来使用 result

        //TODO 注意协程不会帮助我们切换线程，需要手动切换线程
    }
}

fun startMyCouroutine(bolck:suspend()->Unit){
    //suspend方法对象的扩展方法
    bolck.startCoroutine(BaseContinuation())
}

suspend fun loadImage(url: String) = suspendCoroutine<ByteArray> {
    continuation ->
//    val responseBody =

    try {
        // 假设请求成功
        if(url!= ""){
            continuation.resume("ok".encodeToByteArray())
        } else {
            continuation.resumeWithException(RuntimeException("http error"))
        }
    } catch (e:Exception){
        continuation.resumeWithException(e)
    }
}