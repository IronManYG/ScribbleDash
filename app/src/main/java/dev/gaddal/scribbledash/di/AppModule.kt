package dev.gaddal.scribbledash.di

import dev.gaddal.scribbledash.ScribbleDashApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as ScribbleDashApp).applicationScope
    }
}