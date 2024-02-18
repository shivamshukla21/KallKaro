package com.example.kallkaro.Data.Login

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.coroutineContext

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var loginProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvents) {
        validatelogDatawithRules()
        printstate()
        when (event) {
            is LoginUIEvents.EmailChanged -> {
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

            is LoginUIEvents.ResetClicked -> {
                resetclick()
            }
        }
    }

    fun validatelogDatawithRules() {
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

    private fun loginclick() {
        Log.d(TAG, "Inside login click")
        loginUser(email = loginUIState.value.email, password = loginUIState.value.password)
    }

    private fun printstate() {
        Log.d(TAG, "Inside login printstate")
        Log.d(TAG, loginUIState.value.toString())
    }

    private fun resetclick() {
        Log.d(TAG, "Inside reset")
        resetPass(loginUIState.value.email)
    }

    private fun loginUser(email: String, password: String) {
        loginProgress.value = true
        Log.d(TAG, "Inside Login user fun2")
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside Login user fun3")
                Log.d(TAG, "login success = {${it.isSuccessful}}")
                if (it.isSuccessful) {
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

    private fun resetPass(email: String) {
        loginProgress.value = true
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        db.collection("users").whereEqualTo("email", email).get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                Log.d(TAG, "User not registered")
                loginProgress.value = false
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d(TAG, "Email sent")
                        } else {
                            Log.d(TAG, "Error")
                        }
                        loginProgress.value = false
                    }
            }
        }.addOnFailureListener { e ->
            Log.d(TAG, "Error checking user registration status", e)
            loginProgress.value = false
        }
    }
}


//    private fun resetPass(email: String){
//        loginProgress.value = true
//        val auth = FirebaseAuth.getInstance()
//
//        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val isNewUser = task.result?.signInMethods?.isEmpty() ?: true
//                if (!isNewUser) {
//                    auth.sendPasswordResetEmail(email)
//                        .addOnCompleteListener {
//                            if (it.isSuccessful){
//                                Log.d(TAG, "Email sent")
//                            } else{
//                                Log.d(TAG,"Error")
//                            }
//                            loginProgress.value = false
//                        }
//                } else {
//                    Log.d(TAG,"User not registered")
//                    loginProgress.value = false
//                }
//            } else {
//                Log.d(TAG,"Error checking user registration status")
//                loginProgress.value = false
//            }
//        }
//    }
//}


//    private fun resetPass(email: String) {
//        loginProgress.value = true
//        FirebaseAuth
//            .getInstance()
//            .sendPasswordResetEmail(email)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d(TAG, "Email sent")
//                    loginProgress.value = false
//                } else {
//                    Log.d(TAG, "Email not sent")
//                    loginProgress.value = false
//                }
//            }
//    }
//}


//        val newUser = mutableStateOf(true)
//        val auth = FirebaseAuth.getInstance()
//        val signIn = auth.fetchSignInMethodsForEmail(email)
//        signIn.addOnCompleteListener {
//            if(it.isSuccessful){
//                newUser.value = it.result?.signInMethods?.isEmpty()?: true
//                if(!newUser.value){
//                    auth.sendPasswordResetEmail(email)
//                        .addOnCompleteListener {
//                            if (it.isSuccessful){
//                                Log.d(TAG, "Email sent")
//                                loginProgress.value = false
//                            } else{
//                                Log.d(TAG,"error")
//                                loginProgress.value = false
//                            }
//                        }
//                } else{
//                    Log.d(TAG,"User not registered")
//                    loginProgress.value = false
//                }
//            } else{
//                Log.d(TAG,"error")
//                loginProgress.value = false
//            }
//        }
//
//    }
//}