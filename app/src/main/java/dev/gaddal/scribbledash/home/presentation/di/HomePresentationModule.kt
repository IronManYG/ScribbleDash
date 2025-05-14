package dev.gaddal.scribbledash.home.presentation.di

import dev.gaddal.scribbledash.core.data.statistics.DataStoreStatistics
import dev.gaddal.scribbledash.core.domain.statistics.StatisticsPreferences
import dev.gaddal.scribbledash.home.presentation.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homePresentationModule = module {
    singleOf(::DataStoreStatistics) bind StatisticsPreferences::class

    viewModelOf(::HomeViewModel)
}