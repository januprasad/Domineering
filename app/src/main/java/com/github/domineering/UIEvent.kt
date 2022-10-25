package com.github.domineering

sealed class UIEvent {
    sealed class InputEvents() {
        object Up : InputEvents()
        object Down : InputEvents()
    }
}
