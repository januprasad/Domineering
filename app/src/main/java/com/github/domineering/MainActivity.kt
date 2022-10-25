package com.github.domineering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.domineering.ui.theme.DomineeringTheme
import com.google.accompanist.flowlayout.FlowRow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DomineeringTheme {
                // A surface container using the 'background' color from the theme
                Box(modifier = Modifier.fillMaxSize()) {
                    MainApp()
                }
            }
        }
    }
}

sealed class Direction {
    object Up : Direction()
    object Down : Direction()
}
typealias DirectionState = MutableState<Direction>

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val uiViewModel = viewModel(modelClass = StateExampleViewModel::class.java)
    val uiState by remember {
        uiViewModel.beerUIState
    }

    val state: DirectionState = remember {
        mutableStateOf(Direction.Up)
    }

    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Beer Bar", fontSize = 70.sp, style = MaterialTheme.typography.bodyLarge)
            BeerLeftCounter(uiState.beerLeft)
        }
        Text(text = "How many beers you would like to have", fontSize = 17.sp)
        Spacer(modifier = Modifier.height(10.dp))

        AnimatedBeerCounter(state, uiState.selectedBeer)

        Spacer(modifier = Modifier.height(10.dp))
        ButtonBar() { event ->
            uiViewModel.onUIEvent(event)
        }
        Spacer(modifier = Modifier.height(20.dp))
        BeerIcons(uiState.selectedBeer)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedBeerCounter(state: DirectionState, selectedBeer: Int) {
    AnimatedContent(
        targetState = selectedBeer,
        transitionSpec = {
            addAnimation(state.value).using(
                SizeTransform(clip = false)
            )
        }
    ) { value ->
        Text(
            modifier = Modifier.padding(12.dp),
            text = "$value",
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
    }
}

@Composable
fun ButtonBar(event: (UIEvent.InputEvents) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = {
            event(UIEvent.InputEvents.Up)
        }) {
            Text(text = "+         Add", color = Color.White)
        }
        Button(
            onClick = {
                event(UIEvent.InputEvents.Down)
            }
        ) {
            Text(text = "-    Decrease", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BeerIcons(beerCount: Int) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp
    ) {
        repeat(beerCount) {
            Card(
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp),
                    painter = painterResource(id = R.drawable.beer_bottle),
                    contentDescription = "beer_bottle",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Composable
fun BeerLeftCounter(counter: Int) {
    val stockValue = rememberUpdatedState(newValue = counter)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(60.dp)
            .height(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stockValue.value.toString(),
            fontSize = 11.sp,
            style = TextStyle(
                lineHeight = 20.sp,
                fontStyle = FontStyle.Italic
            ),
            color = Color.White
        )
    }
}

@ExperimentalAnimationApi
fun addAnimation(direction: Direction, duration: Int = 800): ContentTransform {
    when (direction) {
        Direction.Down -> return slideInVertically(
            animationSpec = tween(durationMillis = duration)
        ) { height -> -height } + fadeIn(
            animationSpec = tween(durationMillis = duration)
        ) with slideOutVertically(
            animationSpec = tween(durationMillis = duration)
        ) { height -> height } + fadeOut(
            animationSpec = tween(durationMillis = duration)
        )

        Direction.Up -> return slideInVertically(
            animationSpec = tween(durationMillis = duration)
        ) { height -> height } + fadeIn(
            animationSpec = tween(durationMillis = duration)
        ) with slideOutVertically(
            animationSpec = tween(durationMillis = duration)
        ) { height -> -height } + fadeOut(
            animationSpec = tween(durationMillis = duration)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DomineeringTheme {
        MainApp()
    }
}
