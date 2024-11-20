package org.multiplatform.project.screens.widgetscreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.multiplatform.project.screens.widgetscreen.middleware.Navigator
import org.multiplatform.project.screens.widgetscreen.middleware.loggerMiddleware
import org.multiplatform.project.screens.widgetscreen.middleware.navigationMiddleware
import org.multiplatform.project.screens.widgetscreen.middleware.uiActionMiddleware
import org.multiplatform.project.screens.widgetscreen.util.NetworkThunks
import org.multiplatform.project.screens.widgetscreen.util.TimerThunks
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.combineReducers
import org.reduxkotlin.createStore
import org.reduxkotlin.thunk.createThunkMiddleware
import kotlin.coroutines.CoroutineContext

class WidgetScreenStore(
    networkContext: CoroutineContext,
    private val uiContext: CoroutineContext,
    navigator: Navigator,
) {
    private lateinit var subscription: StoreSubscription

    val store = createStore(
        combineReducers(widgetScreenReducer),
        WidgetScreenState.INITIAL_STATE,
        applyMiddleware(
            loggerMiddleware,
            createThunkMiddleware(),
            uiActionMiddleware(NetworkThunks(networkContext), TimerThunks(networkContext)),
            navigationMiddleware(navigator = navigator)
        )
    )

    fun addStateUpdateListener(
        sendUpdate: (WidgetScreenState) -> Unit
    ) {
        subscription = store.subscribe {
            sendUpdate(state)
        }
    }

    fun dispatch(action: Any) {
        CoroutineScope(uiContext).launch {
            store.dispatch(action)
        }
    }

    fun removeStateUpdateListener() {
       //subscription.clear()
    }


    val state: WidgetScreenState
        get() = store.state
}
