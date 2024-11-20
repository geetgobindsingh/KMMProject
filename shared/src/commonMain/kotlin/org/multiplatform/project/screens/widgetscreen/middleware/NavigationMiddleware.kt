package org.multiplatform.project.screens.widgetscreen.middleware

import org.multiplatform.project.screens.widgetscreen.NavigationActions
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.reduxkotlin.Middleware

internal fun navigationMiddleware(navigator: Navigator): Middleware<WidgetScreenState> =
    { store ->
        { next ->
            { action ->
                when (action) {
                    is NavigationActions.GotoScreen -> navigator.goto(action.screen)
                }
                when (action) {

                }
                next(action)
            }
        }
    }

enum class Screen {
    WIDGET_SCREEN,
}

interface Navigator {
    fun goto(screen: Screen)
}
