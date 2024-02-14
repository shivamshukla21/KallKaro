package com.example.kallkaro

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kallkaro.Data.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.Screens.Home
import com.example.kallkaro.Screens.Login
import com.example.kallkaro.Screens.Register
import com.example.kallkaro.ui.theme.ng1

@Composable
fun KallKaro() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ng1
    ) {
        Crossfade(targetState = Router.currentScreen) {currentState ->
            when (currentState.value){
                is Screen.RegisterScreen ->{
                    Register(registrationViewModel = RegistrationViewModel())
                }
                is Screen.LoginScreen -> {
                    Login()
                }
                is Screen.HomeScreen -> {
                    Home(registrationViewModel = RegistrationViewModel())
                }
            }
        }
    }
}
