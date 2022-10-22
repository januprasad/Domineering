package com.github.domineering

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias OutEvents = MutableSharedFlow<UIEvent.OutputEvents>

@HiltViewModel
class SharedFlowExampleViewModel @Inject constructor() : ViewModel() {
    val uiEvent: OutEvents = MutableSharedFlow()
    private val beerUIState = mutableStateOf(BeerUIState())

    fun onUIEvent(event: UIEvent.InputEvents) {
        when (event) {
            UIEvent.InputEvents.Up -> {
                viewModelScope.launch {
//                    uiEvent.emit(UIEvent.OutputEvents.GeneratedValue())
                    beerUIState.value.beerLeft = beerUIState.value.beerLeft - 1
                    beerUIState.value.selectedBeer = beerUIState.value.selectedBeer + 1
                    uiEvent.emit(
                        UIEvent.OutputEvents.BarState(
                            beerUIState = beerUIState
                        )
                    )
                }
            }

            UIEvent.InputEvents.Down -> {
                viewModelScope.launch {
//                    uiEvent.emit(UIEvent.OutputEvents.GeneratedValue())
                    beerUIState.value.beerLeft = beerUIState.value.beerLeft + 1
                    beerUIState.value.selectedBeer = beerUIState.value.selectedBeer - 1
                    uiEvent.emit(
                        UIEvent.OutputEvents.BarState(
                            beerUIState = beerUIState
                        )
                    )
                }
            }
        }
    }
}
