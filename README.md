# Domineering

## _The Perfect Way to Communicate between Composables and ViewModels_

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Domineering is a an example project for handling states of an application and with your composables, also this way of writing handling states will you 100% testability.

## Demo

https://user-images.githubusercontent.com/1284454/197841716-fa7caada-ef54-4a46-9e1d-acb58b6ebff6.mov

## Some code snipptes

Mutable UI State object.
```kotlin
private var _beerUIState = mutableStateOf(BeerUIState())
val beerUIState = _beerUIState
```

UI State class, it will cover every data you have to populate in your app.
```kotlin
@Stable
data class BeerUIState(
    var beerLeft: Int = 16,
    var selectedBeer: Int = 0
)
```

Events can be declared as 
```kotlin
sealed class UIEvent {
    sealed class InputEvents() {
        object Up : InputEvents()
        object Down : InputEvents()
    }
}

```

```kotlin
fun onUIEvent(event: UIEvent.InputEvents) {
        when (event) {
            UIEvent.InputEvents.Up -> {
                _directionState.value = Direction.Up
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
                _directionState.value = Direction.Down
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
```


## License

MIT

**Free Software, Hell Yeah!**



