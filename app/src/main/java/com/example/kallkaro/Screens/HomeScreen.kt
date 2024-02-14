package com.example.kallkaro.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kallkaro.Components.CircularProgressIndicatorfun
import com.example.kallkaro.Components.HeadingTextComponent
import com.example.kallkaro.Data.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.ui.theme.bg

@Composable
fun Home(registrationViewModel: RegistrationViewModel) {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(color = bg)
        .padding(28.dp)
        .padding(top = 40.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = "Login Successful")
            Spacer(modifier = Modifier.size(40.dp))
            Button(onClick = { Router.navigateTo(Screen.LoginScreen) }) {
                Text(text = "Logout")
            }
        }
    }
}