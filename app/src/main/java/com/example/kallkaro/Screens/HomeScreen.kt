package com.example.kallkaro.Screens

import android.app.Activity
import android.nfc.Tag
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.kallkaro.Components.CircularProgressIndicatorfun
import com.example.kallkaro.Components.ConnectButton
import com.example.kallkaro.Components.DeleteButton
import com.example.kallkaro.Components.HeadingTextComponent
import com.example.kallkaro.Components.LogoutButton
import com.example.kallkaro.Components.NormalTextComponent
import com.example.kallkaro.Components.SignInButton
import com.example.kallkaro.Components.TextField
import com.example.kallkaro.Data.HomeScreen.HomeScreenViewModel
import com.example.kallkaro.Data.HomeScreen.HomeUIEvents
import com.example.kallkaro.Data.Login.LoginViewModel
import com.example.kallkaro.Data.Registration.RegistrationUIEvents
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.ui.theme.bg
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.math.log


val logv = MutableSharedFlow<String>()

@Composable
fun Home(homeScreenViewModel: HomeScreenViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Surface (modifier = Modifier
            .fillMaxSize()
            .background(color = bg)
            .padding(28.dp)
            .padding(top = 40.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    NormalTextComponent(value = "Buzz In \uD83E\uDD19")
                }
                Spacer(modifier = Modifier.size(40.dp))
                LogoutButton(viewModel = RegistrationViewModel(), onButtonSelected = {homeScreenViewModel.onEvent(HomeUIEvents.LogoutButtonClicked)})
                Spacer(modifier = Modifier.size(20.dp))
                DeleteButton(viewModel = HomeScreenViewModel(), onButtonSelected = {homeScreenViewModel.onEvent(HomeUIEvents.DeleteButtonClicked)})
                Spacer(modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.size(60.dp))
                TextField(labelvalue = "Username of Recipient", onTextSelected = {homeScreenViewModel.onEvent(HomeUIEvents.NameChanged(it))})
                ConnectButton(viewModel = HomeScreenViewModel(), onButtonSelected = {homeScreenViewModel.onEvent(HomeUIEvents.ConnectButtonClicked)})
                val context = LocalContext.current
                LaunchedEffect(key1 = logv) {
                    logv.collect { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    Log.d("achv", "Yaha tak aaya")
                }
            }
        }
    }
        if (homeScreenViewModel.homeProgress.value) {
            CircularProgressIndicatorfun()
        }
}
}