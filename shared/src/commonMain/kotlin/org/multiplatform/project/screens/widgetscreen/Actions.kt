package org.multiplatform.project.screens.widgetscreen

import org.multiplatform.project.ListItem
import org.multiplatform.project.SharedTimer
import org.multiplatform.project.screens.widgetscreen.middleware.Screen

sealed class Actions {
    object FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val items: List<ListItem>)
    data class FetchingItemsFailedAction(val message: String)

    sealed class Timer {
        data class TimerStartedAction(val sharedTimer: SharedTimer)
        data class IncrementAction(val sharedTimer: SharedTimer)
        data class TimerStoppedAction(val sharedTimer: SharedTimer)
    }
}


internal sealed class NavigationActions {
    data class GotoScreen(val screen: Screen)
}