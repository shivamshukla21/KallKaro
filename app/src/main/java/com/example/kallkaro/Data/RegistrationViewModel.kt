package com.example.kallkaro.Data

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.MainActivity
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.ui.theme.ng2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RegistrationViewModel: ViewModel() {
    private val TAG = RegistrationViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValid = mutableStateOf(false)
    var signUpProgress = mutableStateOf(false)
    private val toastMessage = MutableLiveData<String>()
    val _toastMessage = toastMessage

    fun onEvent(event: RegistrationUIEvents){
        validateDatawithRules()
        printstate()
        when(event){
            is RegistrationUIEvents.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                printstate()
            }
            is RegistrationUIEvents.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
                printstate()
            }
            is RegistrationUIEvents.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printstate()
            }
            is RegistrationUIEvents.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printstate()
            }
            is RegistrationUIEvents.CheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    checked = event.checked
                )
                printstate()
                chkclick()
            }
            is RegistrationUIEvents.RegistrationButtonClicked -> {
                regclick()
            }
            is RegistrationUIEvents.LogoutButtonClicked -> {
                logoutclick()
            }
        }
    }

    private fun regclick(){
        Log.d(TAG, "Inside Signup")
        createUser(email = registrationUIState.value.email, password = registrationUIState.value.password)
    }
    private fun logoutclick(){
        Log.d(TAG, "Inside Home")
        logoutUser()
    }
    private fun chkclick(){
        Log.d(TAG, "Inside checkbox")
    }

    fun validateDatawithRules(){
        val fNameResult = Validator.ValidateFName(
            fname = registrationUIState.value.firstName
        )
        val lNameResult = Validator.ValidateLName(
            lname = registrationUIState.value.lastName
        )
        val EmailResult = Validator.ValidateEmail(
            email = registrationUIState.value.email
        )
        val PswdResult = Validator.ValidatePswd(
            pswd = registrationUIState.value.password
        )
        val chkResult = Validator.ValidateCheck(
            chk = registrationUIState.value.checked
        )
        Log.d(TAG, "Inside ValidatewithRules")
        Log.d(TAG, "Inside $fNameResult")
        Log.d(TAG, "Inside $lNameResult")
        Log.d(TAG, "Inside $EmailResult")
        Log.d(TAG, "Inside $PswdResult")
        Log.d(TAG, "Inside $chkResult")

        registrationUIState.value = registrationUIState.value.copy(
            fNameErr = fNameResult.status,
            lNameErr = lNameResult.status,
            EmailErr = EmailResult.status,
            PswdErr = PswdResult.status,
            chkErr = chkResult.status
        )
    }

    private fun printstate(){
        Log.d(TAG, "Inside reg printstate")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun createUser(email: String, password: String) {
        signUpProgress.value = true
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside oncomplete")
                Log.d(TAG, "Is successful = ${it.isSuccessful}")
                signUpProgress.value = false
                if(it.isSuccessful){
                    Log.d(true.toString(), "Inside Home after registeration")
                    Router.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                signUpProgress.value = false
                Log.d(TAG, "Inside onfailure")
                Log.d(TAG, "Is successful = ${it.message}")
                Log.d(TAG, "Is successful = ${it.localizedMessage}")
            }
    }
    private fun logoutUser(){
        signUpProgress.value = true
        val auth = FirebaseAuth.getInstance()
        val authlistener = AuthStateListener{
            if(it.currentUser == null){
                _toastMessage.value = "Logged Out Successfully"
                Log.d(TAG,"Logged Out Successful")
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    Router.navigateTo(Screen.LoginScreen)
                    signUpProgress.value = false
                }
            } else{
                _toastMessage.value = "Log Out Failed"
                Log.d(TAG, "Logout Fail")
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    signUpProgress.value = false
                }
            }
        }
        auth.addAuthStateListener(authlistener)
        auth.signOut()
    }
}
