package com.dirk.sunfloweranalysis.coroutine.async

import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.*

fun call(){
    startMyCouroutine (DownloadContext("123")){
        ioOperation {
            //TODO 这里为什么会这样？
            val result = loadImage(this[DownloadContext]!!.url)
            //接下来使用 result
            //TODO 注意协程不会帮助我们切换线程，需要手动切换线程
        }
    }
}

fun startMyCouroutine(context: CoroutineContext = EmptyCoroutineContext,bolck:suspend()->Unit){
    //suspend方法对象的扩展方法
    bolck.startCoroutine(ContextContinuation(context+AsyncContext()))
}


suspend fun <T>ioOperation(block:CoroutineContext.()->T) = suspendCoroutine<T> {
    continuation ->

//    val responseBody =
    AsyncTask{
        try {
            continuation.resume(block(continuation.context))
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