package com.example.kallkaro.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.kallkaro.Components.CircularProgressIndicatorfun
import com.example.kallkaro.Components.ClickableTextComp2
import com.example.kallkaro.Components.ClickableTextComp3
import com.example.kallkaro.Components.DividerComp
import com.example.kallkaro.Components.EmTextField
import com.example.kallkaro.Components.HeadingTextComponent
import com.example.kallkaro.Components.KallKaroComp
import com.example.kallkaro.Components.LogButton
import com.example.kallkaro.Components.NormalTextComponent
import com.example.kallkaro.Components.PswdTextField
import com.example.kallkaro.Data.LoginUIEvents
import com.example.kallkaro.Data.LoginViewModel
import com.example.kallkaro.Data.RegistrationUIEvents
import com.example.kallkaro.Data.RegistrationViewModel
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.R
import com.example.kallkaro.ui.theme.bg

@Composable
fun Login (viewModel: LoginViewModel) {
    lateinit var logoutToast : RegistrationViewModel
    val emState = remember { mutableStateOf("") }
    val pswdState = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface (modifier = Modifier
            .fillMaxSize()
            .background(color = bg)
            .padding(28.dp)
            .padding(top = 40.dp)){
            Column(modifier = Modifier.fillMaxSize()){
                NormalTextComponent(value = stringResource(id = R.string.welcome))
                HeadingTextComponent(value = stringResource(id = R.string.sign))
                Spacer(modifier = Modifier.size(100.dp))
                KallKaroComp()
                Spacer(modifier = Modifier.size(50.dp))
                EmTextField(labelvalue = stringResource(id = R.string.eml), Icons.Default.Email, onTextSelected = { viewModel.onEvent(LoginUIEvents.EmailChanged(it)) })
                PswdTextField(labelvalue = stringResource(id = R.string.pswd), onTextSelected = { viewModel.onEvent(LoginUIEvents.PasswordChanged(it)) })
                Spacer(modifier = Modifier.size(30.dp))
                LogButton(viewModel = LoginViewModel(), onButtonSelected = { viewModel.onEvent(LoginUIEvents.LoginButtonClicked) }, EmSt = Validator.ValidatelogEmail(email = viewModel.loginUIState.value.email).status, PswdSt = Validator.ValidatelogPswd(pswd = viewModel.loginUIState.value.password).status)
                Spacer(modifier = Modifier.size(20.dp))
                ClickableTextComp2(value = stringResource(id = R.string.forgot), onTextSelected = {})
                Spacer(modifier = Modifier.size(10.dp))
                DividerComp()
                Spacer(modifier = Modifier.size(20.dp))
                ClickableTextComp3(value = "Don't have an account? Signup", onTextSelected = {})
            }
    }
        if(viewModel.loginProgress.value){
            CircularProgressIndicatorfun()
        }
    }
}

@Preview
@Composable
fun LoginPrev () {
    Login(viewModel = LoginViewModel())
}