package com.dirk.sunfloweranalysis

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//TODO zfc 为什么HiltFragment一定要依赖于HiltActivity，而HiltActivity一定要依赖于 HiltAndroidApp
@HiltAndroidApp
class SunflowerApplication : Application()