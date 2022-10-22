package com.github.domineering

import androidx.compose.runtime.Stable

@Stable
data class BeerUIState(
    var beerLeft: Int = 16,
    var selectedBeer: Int = 0
)

sealed class UIState {
    object Setup : UIState()
    data class Inc(val beerUIState: BeerUIState) : UIState()
    data class Dec(val beerUIState: BeerUIState) : UIState()
}
