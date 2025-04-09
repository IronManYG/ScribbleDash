package dev.gaddal.scribbledash.home.presentation.di

import dev.gaddal.scribbledash.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
}