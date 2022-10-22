package com.github.domineering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.domineering.ui.theme.DomineeringTheme

class BeerBarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DomineeringTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    BeerBarAppState()
//                    BeerBarAppSharedFlow()
                    BeerBarAppStateFlow()
                }
            }
        }
    }
}

@Composable
fun BeerBarAppSharedFlow() {
    val uiViewModel = viewModel(modelClass = SharedFlowExampleViewModel::class.java)
    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Up)
    }) {
        Text(text = "+")
    }

    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Down)
    }) {
        Text(text = "-")
    }

    var viewModelValue by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(key1 = true) {
        uiViewModel.uiEvent.collect { event ->
            viewModelValue = when (event) {
                is UIEvent.OutputEvents.GeneratedValue -> {
                    event.value
                }

                is UIEvent.OutputEvents.BarState -> {
                    event.beerUIState.value.beerLeft
                }
            }
        }
    }
    Text(text = viewModelValue.toString(), fontSize = 50.sp)
}

@Composable
fun BeerBarAppState() {
    val uiViewModel = viewModel(modelClass = StateExampleViewModel::class.java)
    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Up)
    }) {
        Text(text = "+")
    }

    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Down)
    }) {
        Text(text = "-")
    }

    val state = uiViewModel.beerUIState
    Text(text = state.value.beerLeft.toString(), fontSize = 50.sp)
}

@Composable
fun BeerBarAppStateFlow() {
    val uiViewModel = viewModel(modelClass = StateFlowExampleViewModel::class.java)
    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Up)
    }) {
        Text(text = "+")
    }

    Button(onClick = {
        uiViewModel.onUIEvent(UIEvent.InputEvents.Down)
    }) {
        Text(text = "-")
    }

    val state = uiViewModel.beerUIState.collectAsState()
    when (state.value) {
        is UIState.Dec -> {
            val beerUIStateWhenDec = state.value as UIState.Dec
            Text(text = beerUIStateWhenDec.beerUIState.beerLeft.toString(), fontSize = 50.sp)
        }

        is UIState.Inc -> {
            val beerUIStateWhenInc = state.value as UIState.Inc
            Text(text = beerUIStateWhenInc.beerUIState.beerLeft.toString(), fontSize = 50.sp)
        }

        UIState.Setup -> Text(text = "Setting...", fontSize = 50.sp)
    }
}
