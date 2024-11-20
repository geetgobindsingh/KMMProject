package org.multiplatform.project.screens.widgetscreen.middleware

import org.multiplatform.project.screens.widgetscreen.Actions
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.multiplatform.project.screens.widgetscreen.ui.UiActions
import org.multiplatform.project.screens.widgetscreen.util.NetworkThunks
import org.reduxkotlin.middleware

fun uiActionMiddleware(networkThunks: NetworkThunks) = middleware<WidgetScreenState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UiActions.Initialise -> dispatch(networkThunks.fetchItems())
        is UiActions.StartTimer -> dispatch(Actions.Timer.StartTimerAction(action.sharedTimer))
        is UiActions.StopTimer -> dispatch(Actions.Timer.StopTimerAction(action.sharedTimer))
    }
    result
}