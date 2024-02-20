package com.example.kallkaro.Data.Login

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var loginProgress = mutableStateOf(false)
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

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
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside Login user fun3")
                Log.d(TAG, "login success = {${it.isSuccessful}}")
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val userDocRef = db.collection("users").document(user!!.email.toString())
                    if (user.isEmailVerified) {
                        userDocRef.get()
                            .addOnSuccessListener { document ->
                                if (document != null && document.exists()) {
                                    Log.d(TAG, "User details already exist in Firestore")
                                } else {
                                    val us = hashMapOf("email" to email)
                                    userDocRef.set(us)
                                        .addOnSuccessListener {
                                            Log.d(TAG, "User details added to Firestore")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w(TAG, "Error adding user details to Firestore", e)
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error checking user details in Firestore", e)
                            }
                        Log.d("LoginViewModel", "Thread: ${Thread.currentThread().name}")
                        _toastMessage.value = "kr be kuch"
                        Handler(Looper.getMainLooper()).post(){
                            _toastMessage.value = "Something happened"
                            Router.navigateTo(Screen.HomeScreen)
                        }
                        Log.d(true.toString(), "Inside Home after login")
                    } else {
                        auth.signOut()
                        Log.d(TAG, "Email not verified")
                    }
                    loginProgress.value = false
                    Log.d(TAG, "{${toastMessage.value}}")
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

        db.collection("users").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
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

