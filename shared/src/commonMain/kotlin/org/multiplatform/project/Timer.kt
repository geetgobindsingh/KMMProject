package org.multiplatform.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class SharedTimer(val id: Int, val count : Int = 0,  val job: Job? = null)