package com.example.kallkaro.Screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kallkaro.Components.CheckBoxComp
import com.example.kallkaro.Components.ClickableTextComp4
import com.example.kallkaro.Components.DividerComp
import com.example.kallkaro.Components.HeadingTextComponent
import com.example.kallkaro.Components.KallKaroComp
import com.example.kallkaro.Components.NormalTextComponent
import com.example.kallkaro.Components.PswdTextField
import com.example.kallkaro.Components.RegButton
import com.example.kallkaro.Components.TextField
import com.example.kallkaro.Components.EmTextField
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Data.Registration.RegistrationUIEvents
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.Components.CircularProgressIndicatorfun
import com.example.kallkaro.Components.SignInButton
import com.example.kallkaro.Navigation.SystemBackButtonHandler
import com.example.kallkaro.R
import com.example.kallkaro.ui.theme.bg
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun Register(registrationViewModel: RegistrationViewModel) {
    val fNameState = remember { mutableStateOf("") }
    val lNameState = remember { mutableStateOf("") }
    val emState = remember { mutableStateOf("") }
    val pswdState = remember { mutableStateOf("") }
    val checkState = remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity
    val viewModel = remember { RegistrationViewModel() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(color = bg)
            .padding(28.dp)
            .padding(top = 40.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingTextComponent(value = stringResource(id = R.string.create))
                Spacer(modifier = Modifier.size(30.dp))
                KallKaroComp()
                Spacer(modifier = Modifier.size(20.dp))
                TextField(labelvalue = stringResource(id = R.string.fname), onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvents.FirstNameChanged(it))})
                TextField(labelvalue = stringResource(id = R.string.lname), onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvents.LastNameChanged(it))})
                EmTextField(labelvalue = stringResource(id = R.string.eml), onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvents.EmailChanged(it))})
                PswdTextField(labelvalue = stringResource(id = R.string.pswd), onTextSelected = { registrationViewModel.onEvent(RegistrationUIEvents.PasswordChanged(it))})
                CheckBoxComp(value = stringResource(id = R.string.TC), onCheckBoxTick = { registrationViewModel.onEvent(RegistrationUIEvents.CheckBoxClicked(it))})
                RegButton(viewModel = RegistrationViewModel(), onButtonSelected = { registrationViewModel.onEvent(RegistrationUIEvents.RegistrationButtonClicked)}, fNameSt = Validator.ValidateFName(fname = registrationViewModel.registrationUIState.value.firstName).status, lNameSt = Validator.ValidateLName(lname = registrationViewModel.registrationUIState.value.lastName).status, EmSt = Validator.ValidateEmail(email = registrationViewModel.registrationUIState.value.email).status, PswdSt = Validator.ValidatePswd(pswd = registrationViewModel.registrationUIState.value.password).status, ChkSt = Validator.ValidateCheck(chk = registrationViewModel.registrationUIState.value.checked).status)
                Spacer(modifier = Modifier.height(10.dp))
                SignInButton(activity = activity, viewModel = viewModel)
                DividerComp()
                Spacer(modifier = Modifier.height(10.dp))
                ClickableTextComp4(onTextSelected = {})
            }
        }
        if(registrationViewModel.signUpProgress.value){
            CircularProgressIndicatorfun()
        }
    }
    SystemBackButtonHandler {
        Router.navigateTo(Screen.LoginScreen)
    }
}

@Preview
@Composable
fun RegisterPreview(){
    Register(registrationViewModel = RegistrationViewModel())
}