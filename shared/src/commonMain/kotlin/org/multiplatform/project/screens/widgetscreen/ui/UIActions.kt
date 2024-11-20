package org.multiplatform.project.screens.widgetscreen.ui

import org.multiplatform.project.SharedTimer

sealed class UiActions {
    object Initialise
    data class StartTimer(val sharedTimer: SharedTimer)
    data class StopTimer(val sharedTimer: SharedTimer)
}