package com.github.domineering

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StateFlowExampleViewModel @Inject constructor() : ViewModel() {
    private val _beerUIState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Setup)
    val beerUIState = _beerUIState
    var beerLeft = mutableStateOf(16)
    var selectedBeer = mutableStateOf(0)

    fun onUIEvent(event: UIEvent.InputEvents) {
        when (event) {
            UIEvent.InputEvents.Up -> {
                beerLeft.value = -1
                selectedBeer.value = +1
                _beerUIState.value = UIState.Inc(
                    beerUIState = BeerUIState(
                        beerLeft.value,
                        selectedBeer.value
                    )
                )
            }

            UIEvent.InputEvents.Down -> {
                beerLeft.value = +1
                selectedBeer.value = -1
                _beerUIState.value = UIState.Inc(
                    beerUIState = BeerUIState(
                        beerLeft.value,
                        selectedBeer.value
                    )
                )
            }
        }
    }
}
