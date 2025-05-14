package dev.gaddal.scribbledash.core.data.statistics

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.domain.statistics.StatisticsPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreStatistics(
    private val context: Context
) : StatisticsPreferences {

    companion object {
        private val Context.statisticsDataStore by preferencesDataStore(
            name = "statistics_datastore"
        )
    }

    private fun getHighestAccuracyKey(mode: GameMode) =
        floatPreferencesKey("${mode::class.simpleName?.lowercase()}_highest_accuracy")

    private fun getMostDrawingsCompletedKey(mode: GameMode) =
        intPreferencesKey("${mode::class.simpleName?.lowercase()}_most_drawings_completed")

    override suspend fun saveHighestAccuracy(mode: GameMode, accuracy: Float) {
        context.statisticsDataStore.edit { prefs ->
            prefs[getHighestAccuracyKey(mode)] = accuracy
        }
    }

    override fun observeHighestAccuracy(mode: GameMode): Flow<Float> {
        return context
            .statisticsDataStore
            .data
            .map { prefs ->
                prefs[getHighestAccuracyKey(mode)]?.toFloat() ?: 0f
            }
            .distinctUntilChanged()
    }

    override suspend fun saveMostDrawingsCompleted(mode: GameMode, count: Int) {
        context.statisticsDataStore.edit { prefs ->
            prefs[getMostDrawingsCompletedKey(mode)] = count
        }
    }

    override fun observeMostDrawingsCompleted(mode: GameMode): Flow<Int> {
        return context
            .statisticsDataStore
            .data
            .map { prefs ->
                prefs[getMostDrawingsCompletedKey(mode)]?.toInt() ?: 0
            }
            .distinctUntilChanged()
    }
}