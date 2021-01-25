package com.dirk.sunfloweranalysis.coroutine.async

import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

fun call(){
    startMyCouroutine {
        ioOperation {
            val result = loadImage("123")
            //接下来使用 result
            //TODO 注意协程不会帮助我们切换线程，需要手动切换线程
        }
    }
}

fun startMyCouroutine(bolck:suspend()->Unit){
    //suspend方法对象的扩展方法
    bolck.startCoroutine(ContextContinuation(AsyncContext()))
}


suspend fun <T>ioOperation(block:()->T) = suspendCoroutine<T> {
    continuation ->

//    val responseBody =
    AsyncTask{
        try {
            continuation.resume(block())
        } catch (e:Exception){
            continuation.resumeWithException(e)
        }
    }.execute()

}

fun loadImage(url: String) =
            // 假设请求成功
            if(url!= ""){
                "ok".encodeToByteArray()
            } else {
                throw (RuntimeException("http error"))
            }


/**
 * 假设执行了线程切换
 */
fun changeUiThread(block:()->Unit){
    block()
}