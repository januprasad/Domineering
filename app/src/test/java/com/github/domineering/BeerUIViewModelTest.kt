package com.github.domineering

import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class BeerUIViewModelTest {
    lateinit var stateExampleViewModel: BeerUIStateViewModel

    @Before
    fun setup() {
        stateExampleViewModel = BeerUIStateViewModel()
    }

    @Test
    fun testBeerLeftAfterUp() {
        val beerLeftBefore = stateExampleViewModel.beerUIState.value.beerLeft
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        val beerLeftAfter = stateExampleViewModel.beerUIState.value.beerLeft
        Assert.assertEquals(beerLeftBefore, 16)
        Assert.assertEquals(beerLeftAfter, 13)
    }

    @Test
    fun testBeerCountAfterUp() {
        val beerCountBefore = stateExampleViewModel.beerUIState.value.selectedBeer
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Up)
        val beerCountAfter = stateExampleViewModel.beerUIState.value.selectedBeer
        Assert.assertEquals(beerCountBefore, 0)
        Assert.assertEquals(beerCountAfter, 3)
    }

    @Test
    fun testBeerCountAfterDown() {
        val beerCountBefore = stateExampleViewModel.beerUIState.value.selectedBeer
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        val beerCountAfter = stateExampleViewModel.beerUIState.value.selectedBeer
        Assert.assertEquals(beerCountBefore, 0)
        Assert.assertEquals(beerCountAfter, 0)
    }

    @Test
    fun testBeerLeftAfterDown() {
        val beerLeftBefore = stateExampleViewModel.beerUIState.value.beerLeft
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        stateExampleViewModel.onUIEvent(UIEvent.InputEvents.Down)
        val beerLeftAfter = stateExampleViewModel.beerUIState.value.beerLeft
        Assert.assertEquals(beerLeftBefore, 16)
        Assert.assertEquals(beerLeftAfter, 16)
    }
}
