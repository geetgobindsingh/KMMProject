package org.multiplatform.project

sealed class ListItem(val contentType: ListItemContentType) {
    data class TimerItem(val timer: SharedTimer) : ListItem(ListItemContentType.TIMER)
    data class ImageItem(val id: String, val image: Image) : ListItem(ListItemContentType.IMAGE)
}

enum class ListItemContentType {
    IMAGE, TIMER
}