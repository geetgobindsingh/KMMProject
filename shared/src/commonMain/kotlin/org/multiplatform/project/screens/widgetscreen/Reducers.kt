package org.multiplatform.project.screens.widgetscreen

import kotlinx.coroutines.delay
import org.multiplatform.project.ListItem
import org.multiplatform.project.ListItemContentType
import org.multiplatform.project.screens.widgetscreen.reducers.itemReducer
import org.reduxkotlin.Reducer
import org.reduxkotlin.combineReducers

val widgetFetchReducer: Reducer<WidgetScreenState> = { state, action ->
    when (action) {
        is Actions.FetchingItemsStartedAction -> state.copy(
            isLoading = true,
            widgets = emptyList(),
            errorMessage = null
        )

        is Actions.FetchingItemsSuccessAction -> state.copy(
            isLoading = false,
            widgets = action.items,
            errorMessage = null
        )

        is Actions.FetchingItemsFailedAction -> state.copy(
            isLoading = false,
            widgets = emptyList(),
            errorMessage = action.message
        )
        else -> state
    }
}

val timerReducer: Reducer<WidgetScreenState> = { state, action ->
    when (action) {
        is Actions.Timer.IncrementAction -> {
            state.copy(widgets = state.widgets.map { widget ->
                if (widget.contentType == ListItemContentType.TIMER) {
                    if ((widget as ListItem.TimerItem).timer.id == action.sharedTimer.id) {
                        widget.copy(timer = widget.timer.copy(count = widget.timer.count + 1))
                    } else {
                        widget
                    }
                } else {
                    widget
                }
            })
        }
        is Actions.Timer.TimerStartedAction -> {
            state.copy(widgets = state.widgets.map { widget ->
                if (widget.contentType == ListItemContentType.TIMER) {
                    if ((widget as ListItem.TimerItem).timer.id == action.sharedTimer.id) {
                        widget.copy(timer = widget.timer.copy(job = action.sharedTimer.job, count = 0))
                    } else {
                        widget
                    }
                } else {
                    widget
                }
            })
        }
        is Actions.Timer.TimerStoppedAction -> {
            state.copy(widgets = state.widgets.map { widget ->
                if (widget.contentType == ListItemContentType.TIMER) {
                    if ((widget as ListItem.TimerItem).timer.id == action.sharedTimer.id) {
                        widget.copy(timer = widget.timer.copy(job = action.sharedTimer.job, count = 0))
                    } else {
                        widget
                    }
                } else {
                    widget
                }
            })
        }

        else -> state
    }
}

val widgetScreenReducer: Reducer<WidgetScreenState> = combineReducers(widgetFetchReducer, timerReducer)