package dev.gaddal.scribbledash.core.domain.statistics

import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import kotlinx.coroutines.flow.Flow

interface StatisticsPreferences {
    suspend fun saveHighestAccuracy(mode: GameMode, accuracy: Float)
    fun observeHighestAccuracy(mode: GameMode): Flow<Float>

    suspend fun saveMostDrawingsCompleted(mode: GameMode, count: Int)
    fun observeMostDrawingsCompleted(mode: GameMode): Flow<Int>
}