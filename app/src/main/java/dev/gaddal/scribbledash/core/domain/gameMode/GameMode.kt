package dev.gaddal.scribbledash.core.domain.gameMode

import androidx.compose.ui.graphics.Color
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.ui.UiText

sealed class GameMode(
    val name: UiText,
    val imageResId: Int,
    val backgroundColor: Color,
    val shadowColor: Color,
) {
    data object OneRoundWonder : GameMode(
        name = UiText.StringResource(R.string.one_round_wonder),
        imageResId = R.drawable.one_round_wonder,
        backgroundColor = AppColors.SuccessGreen,
        shadowColor = Color(0xFF12B974).copy(alpha = 0.75f),
    )

    companion object {
        // This is a list of all game modes
        val allGameModes = listOf(
            OneRoundWonder,
        )
    }
}