package org.multiplatform.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SharedTimer(val id: Int) {
    private val _time = MutableStateFlow(0)
    val time: StateFlow<Int> get() = _time

    private var job: Job? = null

    fun start() {
        println("Kotlin timer start called")
        if (job == null) {
            job = CoroutineScope(Dispatchers.Default).launch {
                while (true) {
                    delay(100)
                    _time.value += 1
                }
            }
        }
    }

    fun stop() {
        println("Kotlin timer stop called")
        job?.cancel()
        job = null
    }
}

object TimerRepository {
    private val timers = List(100) { SharedTimer(it) }

    fun getTimers(): List<SharedTimer> = timers
}