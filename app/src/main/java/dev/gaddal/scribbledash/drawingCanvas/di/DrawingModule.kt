package dev.gaddal.scribbledash.drawingCanvas.di

import dev.gaddal.scribbledash.drawingCanvas.data.DrawingRepository
import dev.gaddal.scribbledash.drawingCanvas.data.DrawingResources
import dev.gaddal.scribbledash.drawingCanvas.domain.PathComparisonEngine
import org.koin.dsl.module

val drawingModule = module {

    // 1) DrawingRepository as a singleton
    single {
        DrawingRepository(get(), DrawingResources.allDrawingIds)
    }

    // 2) PathComparisonEngine
    single { PathComparisonEngine() }
}