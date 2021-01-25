package com.dirk.sunfloweranalysis.coroutine.async

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class DownloadContext (val url:String):AbstractCoroutineContextElement(Key){
    companion object Key:CoroutineContext.Key<DownloadContext>
}