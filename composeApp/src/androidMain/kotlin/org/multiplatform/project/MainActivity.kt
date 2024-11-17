package org.multiplatform.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: TimerViewModel by viewModels()

        setContent {
            TimerApp(viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}