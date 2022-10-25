package com.github.domineering

import androidx.compose.runtime.Stable

@Stable
data class BeerUIState(
    var beerLeft: Int = 16,
    var selectedBeer: Int = 0
)
