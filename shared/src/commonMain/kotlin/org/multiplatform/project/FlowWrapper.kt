package org.multiplatform.project

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// Modify the timer's StateFlow to use a callback interface or function.
//fun SharedTimer.addValueUpdateHandler(callback: (Int) -> Unit) {
//    MainScope().launch {
//        time.collect { value ->
//            callback(value)
//        }
//    }
//}