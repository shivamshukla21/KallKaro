package com.example.kallkaro.Navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object RegisterScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}