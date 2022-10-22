package com.github.domineering

import androidx.compose.runtime.MutableState

sealed class UIEvent {
    sealed class InputEvents() {
        object Up : InputEvents()
        object Down : InputEvents()
    }

    sealed class OutputEvents {
        data class GeneratedValue(val value: Int = (1..10000).shuffled().random()) : OutputEvents()
        class BarState(val beerUIState: MutableState<BeerUIState>) : OutputEvents()
    }
}
