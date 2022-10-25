package com.github.domineering

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerUIStateViewModel @Inject constructor() : ViewModel() {
    private var _beerUIState = mutableStateOf(BeerUIState())
    val beerUIState = _beerUIState

    fun onUIEvent(event: UIEvent.InputEvents) {
        when (event) {
            UIEvent.InputEvents.Up -> {
                when {
                    2 != _beerUIState.value.beerLeft -> {
                        _beerUIState.value = beerUIState.value.copy(
                            beerLeft = beerUIState.value.beerLeft - 1,
                            selectedBeer = beerUIState.value.selectedBeer + 1
                        )
                    }
                }
            }

            UIEvent.InputEvents.Down -> {
                when {
                    0 != _beerUIState.value.selectedBeer -> {
                        _beerUIState.value = beerUIState.value.copy(
                            beerLeft = beerUIState.value.beerLeft + 1,
                            selectedBeer = beerUIState.value.selectedBeer - 1
                        )
                    }
                }
            }
        }
    }
}
