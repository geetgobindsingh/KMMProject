package org.multiplatform.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.multiplatform.project.screens.widgetscreen.WidgetScreenStore
import org.multiplatform.project.screens.widgetscreen.ui.UiActions

class TimerViewModel : ViewModel() {

    private val widgetScreenStore: WidgetScreenStore = WidgetScreenStore(Dispatchers.IO, viewModelScope.coroutineContext)

    private val _widgetScreenState: MutableStateFlow<WidgetScreenState> = MutableStateFlow(widgetScreenStore.state)

    val widgetScreenState: Flow<WidgetScreenState> = _widgetScreenState.asStateFlow()

    init {
        widgetScreenStore.addStateUpdateListener { state ->
            _widgetScreenState.value = state
        }
        widgetScreenStore.dispatch(UiActions.Initialise())
    }


    fun startTimer(timer: SharedTimer) {
        timer.start()
    }

    fun stopTimer(timer: SharedTimer) {
        timer.stop()
    }
}