package com.example.kallkaro.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kallkaro.Components.CircularProgressIndicatorfun
import com.example.kallkaro.Components.HeadingTextComponent
import com.example.kallkaro.Components.LogoutButton
import com.example.kallkaro.Data.Registration.RegistrationUIEvents
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.ui.theme.bg

@Composable
fun Home(registrationViewModel: RegistrationViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Surface (modifier = Modifier
            .fillMaxSize()
            .background(color = bg)
            .padding(28.dp)
            .padding(top = 40.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Login Successful")
                Spacer(modifier = Modifier.size(40.dp))
                LogoutButton(viewModel = RegistrationViewModel(), onButtonSelected = {registrationViewModel.onEvent(
                    RegistrationUIEvents.LogoutButtonClicked)})
            }
    }
        if(registrationViewModel.signUpProgress.value){
            CircularProgressIndicatorfun()
        }
    }
}