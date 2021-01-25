package com.dirk.sunfloweranalysis.coroutine.async

import java.util.concurrent.Executors


private val pool by lazy {
    Executors.newCachedThreadPool()
}

class AsyncTask (val block:()->Unit){
    fun execute() = pool.execute(block)
}