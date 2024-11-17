package org.multiplatform.project

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object ItemsRepository {
    private val timers = List(100) { SharedTimer(it) }
    private val images = List(100) { Image() }

    @OptIn(ExperimentalUuidApi::class)
    fun getItems(): List<ListItem> = arrayListOf<ListItem>().also { list ->
        (0..(100 - 1)).forEach { index ->
            list.add(ListItem.TimerItem(timers[index]))
            list.add(ListItem.ImageItem(Uuid.random().toString(), images[index]))
        }
    }
}