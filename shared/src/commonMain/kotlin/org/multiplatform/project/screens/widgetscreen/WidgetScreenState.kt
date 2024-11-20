package org.multiplatform.project.screens.widgetscreen

import org.multiplatform.project.ListItem

data class WidgetScreenState(
    val isLoading: Boolean = false,
    val widgets: List<ListItem> = emptyList(),
    val errorMessage: String? = null
) {
    companion object {
        val INITIAL_STATE = WidgetScreenState()
    }
}

