package org.multiplatform.project.screens.widgetscreen.middleware

import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.multiplatform.project.screens.widgetscreen.ui.UiActions
import org.multiplatform.project.screens.widgetscreen.util.NetworkThunks
import org.reduxkotlin.middleware

fun uiActionMiddleware(networkThunks: NetworkThunks) = middleware<WidgetScreenState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is UiActions.Initialise -> dispatch(networkThunks.fetchItems())
    }
    result
}