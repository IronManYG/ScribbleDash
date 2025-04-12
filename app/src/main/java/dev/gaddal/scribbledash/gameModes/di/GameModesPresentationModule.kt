package dev.gaddal.scribbledash.gameModes.di

import dev.gaddal.scribbledash.drawingCanvas.data.DefaultCanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.OneRoundWonderViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val gameModesPresentationModule = module {
    single<CanvasController> { DefaultCanvasController() }

    viewModelOf(::OneRoundWonderViewModel)
}