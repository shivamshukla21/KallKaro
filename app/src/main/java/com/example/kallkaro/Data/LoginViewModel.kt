package com.example.kallkaro.Data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var loginProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvents){
        validatelogDatawithRules()
        printstate()
        when(event){
            is LoginUIEvents.EmailChanged-> {
                Log.d(TAG, "Email changing")
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
                printstate()
            }
            is LoginUIEvents.PasswordChanged -> {
                Log.d(TAG, "passwd changing")
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
                printstate()
            }
            is LoginUIEvents.LoginButtonClicked -> {
                loginclick()
            }
        }
    }
    fun validatelogDatawithRules(){
        val EmailResult = Validator.ValidatelogEmail(
            email = loginUIState.value.email
        )
        val PswdResult = Validator.ValidatelogPswd(
            pswd = loginUIState.value.password
        )
        Log.d(TAG, "Inside login ValidatewithRules")
        Log.d(TAG, "Inside $EmailResult")
        Log.d(TAG, "Inside $PswdResult")

        loginUIState.value = loginUIState.value.copy(
            EmailErr = EmailResult.status,
            PswdErr = PswdResult.status
        )
    }
    private fun loginclick(){
        Log.d(TAG, "Inside login click")
        loginUser(email = loginUIState.value.email, password = loginUIState.value.password)
    }
    private fun printstate(){
        Log.d(TAG, "Inside login printstate")
        Log.d(TAG, loginUIState.value.toString())
    }
    private fun loginUser(email: String, password: String){
        loginProgress.value = true
        Log.d(TAG, "Inside Login user fun2")
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside Login user fun3")
                Log.d(TAG, "login success = {${it.isSuccessful}}")
                if(it.isSuccessful){
                    Log.d(true.toString(), "Inside Home after login")
                    Router.navigateTo(Screen.HomeScreen)
                    loginProgress.value = false
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside Fail Login")
                Log.d(TAG, "login success = {${it.localizedMessage}}")
                loginProgress.value = false
            }
    }
}