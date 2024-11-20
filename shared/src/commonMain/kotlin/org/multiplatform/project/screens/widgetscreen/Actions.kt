package org.multiplatform.project.screens.widgetscreen

import org.multiplatform.project.ListItem
import org.multiplatform.project.screens.widgetscreen.middleware.Screen

sealed class Actions {
    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val items: List<ListItem>)
    data class FetchingItemsFailedAction(val message: String)

}


internal sealed class NavigationActions {
    data class GotoScreen(val screen: Screen)
}