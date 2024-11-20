package org.multiplatform.project.screens.widgetscreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import org.multiplatform.project.screens.widgetscreen.middleware.uiActionMiddleware
import org.multiplatform.project.screens.widgetscreen.util.NetworkThunks
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.combineReducers
import org.reduxkotlin.createStore
import org.reduxkotlin.thunk.createThunkMiddleware
import kotlin.coroutines.CoroutineContext

class WidgetScreenStore(
    networkContext: CoroutineContext,
    private val uiContext: CoroutineContext
) {
    val store = createStore(
        combineReducers(screenStateReducer, timerReducer),
        WidgetScreenState.INITIAL_STATE,
        applyMiddleware(
            createThunkMiddleware(),
            uiActionMiddleware(NetworkThunks(networkContext))
        )
    )

    fun addStateUpdateListener(
        sendUpdate: (WidgetScreenState) -> Unit
    ) {
        store.subscribe {
            sendUpdate(state)
        }
    }

    fun dispatch(action: Any) {
        CoroutineScope(uiContext).launch {
            store.dispatch(action)
        }
    }


    val state: WidgetScreenState
        get() = store.state
}
