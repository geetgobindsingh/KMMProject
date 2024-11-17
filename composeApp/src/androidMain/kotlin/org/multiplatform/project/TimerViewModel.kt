package org.multiplatform.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    val items: StateFlow<List<ListItem>> get() = _items

    init {
        loadTimers()
    }

    private fun loadTimers() {
        viewModelScope.launch {
            _items.value = ItemsRepository.getItems()
        }
    }

    fun startTimer(timer: SharedTimer) {
        timer.start()
    }

    fun stopTimer(timer: SharedTimer) {
        timer.stop()
    }
}