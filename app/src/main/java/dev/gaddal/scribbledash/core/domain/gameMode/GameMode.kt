package dev.gaddal.scribbledash.core.domain.gameMode

import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.ui.UiText

sealed class GameMode(
    val name: UiText,
    val imageResId: Int,
) {
    data object OneRoundWonder : GameMode(
        name = UiText.StringResource(R.string.one_round_wonder),
        imageResId = R.drawable.one_round_wonder
    )

    companion object {
        // This is a list of all game modes
        val allGameModes = listOf(
            OneRoundWonder,
        )
    }
}