package org.multiplatform.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.multiplatform.project.modifiers.recomposeHighlighter

@Composable
fun TimerApp(viewModel: TimerViewModel) {
    val timers by viewModel.timers.collectAsState()

    LazyColumn {
        items(timers, key = {timer -> timer.id}) { timer ->
            TimerItem(timer)
        }
    }
}

@Composable
fun TimerItem(timer: SharedTimer) {
    val time by timer.time.collectAsState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .recomposeHighlighter()) {
        Text(text = "Timer ${timer.id}: $time", modifier = Modifier.weight(1f))
        Button(onClick = timer::start ) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = timer::stop ) {
            Text(text = "Stop")
        }
    }
}