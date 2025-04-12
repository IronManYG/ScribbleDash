package dev.gaddal.scribbledash.core.domain.gameMode

import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.ui.UiText

sealed class Level(
    val title: UiText,
    val icon: Int,
) {
    object Beginner : Level(
        title = UiText.StringResource(R.string.beginner),
        icon = R.drawable.beginner,
    )

    object Challenging : Level(
        title = UiText.StringResource(R.string.challenging),
        icon = R.drawable.challenging
    )

    object Master : Level(
        title = UiText.StringResource(R.string.master),
        icon = R.drawable.master,
    )
}
