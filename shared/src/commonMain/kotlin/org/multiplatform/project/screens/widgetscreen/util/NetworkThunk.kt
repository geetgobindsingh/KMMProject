package org.multiplatform.project.screens.widgetscreen.util

import kotlinx.coroutines.*
import org.multiplatform.project.ItemsRepository
import org.multiplatform.project.screens.widgetscreen.Actions
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.reduxkotlin.thunk.Thunk
import kotlin.coroutines.CoroutineContext

/**
 * Thunks are functions that are executed by the "ThunkMiddleware".  They are asynchronous and dispatch
 * actions.  This allows dispatching a loading, success, and failure state.
 */
class NetworkThunks(private val networkContext: CoroutineContext) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = networkContext + job

    fun fetchItems(): Thunk<WidgetScreenState> = { dispatch, getState, extraArg ->
        dispatch(Actions.FetchingItemsStartedAction())
        launch {
            val result = ItemsRepository.getItems()
            delay(1000)
            if (result.isSuccess) {
                dispatch(Actions.FetchingItemsSuccessAction(result.getOrElse { emptyList() }))
            } else {
                dispatch(Actions.FetchingItemsFailedAction("ERROR"))
            }
        }
    }
}

