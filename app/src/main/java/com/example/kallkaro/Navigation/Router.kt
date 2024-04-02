package com.example.kallkaro.Navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.grpc.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

sealed class Screen() {
    object RegisterScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
    object JoinScreen : Screen()
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.JoinScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
    fun updateScreenBasedOnAuthStatus(isAuthenticated: Boolean) {
        currentScreen.value = if (isAuthenticated) {
            Screen.JoinScreen
        } else {
            Screen.JoinScreen
        }
    }
}