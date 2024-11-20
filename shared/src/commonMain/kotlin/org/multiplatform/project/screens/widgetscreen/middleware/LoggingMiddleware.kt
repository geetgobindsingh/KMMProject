package org.multiplatform.project.screens.widgetscreen.middleware

import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.reduxkotlin.middleware

val loggerMiddleware = middleware<WidgetScreenState> { store, next, action ->
    println("********************************************")
    println("* DISPATCHED action: $action")
    println("********************************************")
    next(action)
}