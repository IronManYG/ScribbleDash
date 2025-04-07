package dev.gaddal.scribbledash.core.presentation.designsystem.components.util

import androidx.compose.ui.Modifier

inline fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier {
    return if(condition) {
        this.then(modifier())
    } else this
}