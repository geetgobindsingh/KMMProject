package org.multiplatform.project.screens.widgetscreen

import kotlinx.coroutines.delay
import org.multiplatform.project.screens.widgetscreen.reducers.itemReducer
import org.reduxkotlin.Reducer

inline fun <reified T> reducer(crossinline reducer: ((T, Any) -> T)): Reducer<T> = { state: T, action: Any ->
    reducer(state, action)
}

val reducer: Reducer<WidgetScreenState> = { state, action ->
    when (action) {
        is Actions.FetchingItemsStartedAction -> state.copy(isLoading = true, widgets = emptyList(), errorMessage = null)
        is Actions.FetchingItemsSuccessAction -> state.copy(isLoading = false, widgets = action.items, errorMessage = null)
        is Actions.FetchingItemsFailedAction -> state.copy(isLoading = false, widgets = emptyList(), errorMessage = action.message)
        else -> state
    }
}