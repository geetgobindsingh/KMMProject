package org.multiplatform.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.multiplatform.project.modifiers.recomposeHighlighter
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState

@Composable
fun TimerApp(viewModel: TimerViewModel) {
    val widgetScreenState = viewModel.widgetScreenState.collectAsStateWithLifecycle(WidgetScreenState.INITIAL_STATE)

    val items = widgetScreenState.value.widgets
    LazyColumn {
        items(
            count = items.size,
            key = { index ->
                when(items[index].contentType) {
                    ListItemContentType.TIMER -> { (items[index] as ListItem.TimerItem).timer.id}
                    ListItemContentType.IMAGE -> { (items[index] as ListItem.ImageItem).id}
                }
            },
            contentType = { index ->
                items[index].contentType
            },
        ) { index ->
            val listItem = items[index]
            when(listItem.contentType) {
                ListItemContentType.TIMER -> { TimerItemView(listItem as ListItem.TimerItem) }
                ListItemContentType.IMAGE -> { ImageItemView(listItem as ListItem.ImageItem) }
            }
        }
    }
}

@Composable
fun TimerItemView(timerItem: ListItem.TimerItem) {
    val time by timerItem.timer.time.collectAsState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .recomposeHighlighter(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Timer ${timerItem.timer.id}: $time", modifier = Modifier.weight(1f))
        Button(onClick = timerItem.timer::start ) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = timerItem.timer::stop ) {
            Text(text = "Stop")
        }
    }
}


@Composable
fun ImageItemView(imageItem: ListItem.ImageItem) {
    Box(modifier = Modifier.fillMaxWidth().height(50.dp).recomposeHighlighter(),
        contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageItem.image.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
