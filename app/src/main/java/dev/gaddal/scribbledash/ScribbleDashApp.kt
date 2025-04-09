package dev.gaddal.scribbledash

import android.app.Application
import dev.gaddal.scribbledash.di.appModule
import dev.gaddal.scribbledash.home.presentation.di.homePresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class ScribbleDashApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@ScribbleDashApp)
            modules(
                appModule,
                homePresentationModule,
            )
        }
    }
}