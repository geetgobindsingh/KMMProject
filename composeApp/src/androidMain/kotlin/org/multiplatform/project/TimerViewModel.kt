package org.multiplatform.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _timers = MutableStateFlow<List<SharedTimer>>(emptyList())
    val timers: StateFlow<List<SharedTimer>> get() = _timers

    init {
        loadTimers()
    }

    private fun loadTimers() {
        viewModelScope.launch {
            _timers.value = TimerRepository.getTimers()
        }
    }

    fun startTimer(timer: SharedTimer) {
        timer.start()
    }

    fun stopTimer(timer: SharedTimer) {
        timer.stop()
    }
}