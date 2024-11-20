package org.multiplatform.project.screens.widgetscreen.middleware

import org.multiplatform.project.screens.widgetscreen.Actions
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.multiplatform.project.screens.widgetscreen.ui.UiActions
import org.multiplatform.project.screens.widgetscreen.util.NetworkThunks
import org.multiplatform.project.screens.widgetscreen.util.TimerThunks
import org.reduxkotlin.middleware

fun uiActionMiddleware(
    networkThunks: NetworkThunks,
    timerThunks: TimerThunks
) = middleware<WidgetScreenState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UiActions.Initialise -> dispatch(networkThunks.fetchItems())
        is UiActions.StartTimer -> dispatch(timerThunks.startTimer(action.sharedTimer))
        is UiActions.StopTimer -> dispatch(timerThunks.stopTimer(action.sharedTimer))
    }
    result
}