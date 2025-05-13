package dev.gaddal.scribbledash.core.domain.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale

object Timer {
    /**
     * Creates a countdown timer flow that emits remaining seconds
     */
    fun createCountdownFlow(
        totalTimeSeconds: Int,
        intervalMillis: Long = 1000
    ): Flow<Int> = flow {
        var remainingSeconds = totalTimeSeconds
        emit(remainingSeconds)

        while (remainingSeconds > 0) {
            delay(intervalMillis)
            remainingSeconds--
            emit(remainingSeconds)
        }
    }
}

fun Int.formatted(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format(Locale.US, "%d:%02d", minutes, seconds)
}