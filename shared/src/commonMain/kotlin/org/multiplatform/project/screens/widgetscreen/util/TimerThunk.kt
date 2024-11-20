package org.multiplatform.project.screens.widgetscreen.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.multiplatform.project.ItemsRepository
import org.multiplatform.project.SharedTimer
import org.multiplatform.project.screens.widgetscreen.Actions
import org.multiplatform.project.screens.widgetscreen.WidgetScreenState
import org.reduxkotlin.thunk.Thunk
import kotlin.coroutines.CoroutineContext

class TimerThunks(private val networkContext: CoroutineContext) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = networkContext + job

    fun startTimer(sharedTimer: SharedTimer): Thunk<WidgetScreenState> = { dispatch, getState, extraArg ->
        var job = sharedTimer.job
        if (job == null) {
            job = CoroutineScope(coroutineContext).launch {
                while (true) {
                    delay(100)
                    dispatch(Actions.Timer.IncrementAction(sharedTimer))
                }
            }

            dispatch(Actions.Timer.TimerStartedAction(sharedTimer.copy(job = job)))
        }
    }

    fun stopTimer(sharedTimer: SharedTimer): Thunk<WidgetScreenState> = { dispatch, getState, extraArg ->
        println("Kotlin timer stop called")
        var job = sharedTimer.job
        job?.cancel()
        job = null
        dispatch(Actions.Timer.TimerStoppedAction(sharedTimer.copy(job = job)))
    }
}