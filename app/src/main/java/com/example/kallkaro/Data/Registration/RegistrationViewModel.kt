package com.example.kallkaro.Data.Registration

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

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
                viewModelScope.launch {
                    regclick()
                }
            }
            is RegistrationUIEvents.LogoutButtonClicked -> {
                logoutclick()
            }
        }
    }

    private suspend fun regclick(){
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

    private suspend fun createUser(email: String, password: String) {
        signUpProgress.value = true
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser!!.sendEmailVerification()
                        .addOnCompleteListener { Log.d(TAG, "Verification email sent to ${firebaseUser!!.email}") }
                        .addOnFailureListener { Log.d(TAG, "Verification email not sent") }
                    GlobalScope.launch(Dispatchers.IO) {
                        val time = measureTimeMillis {
                            val emailVerified = async { sendEmailVerificationAndAwait(firebaseUser!!) }
                            Log.d(TAG, "before email verific await")
                            if (emailVerified.await() == true) {
                                signUpProgress.value = false
                                val user = hashMapOf("email" to email)
                                db.collection("users").document(email).set(user)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "User added to Firestore")
                                        Router.navigateTo(Screen.HomeScreen)
                                        Log.d(TAG, "Inside Home after registration")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.d(TAG, "Error adding user to Firestore", e)
                                    }
                            } else {
                                signUpProgress.value = false
                                Log.d(TAG, "Email not verified")
                            }
                        }
                        Log.d(TAG, "${time} mili seconds")
                    }
                } else {
                    signUpProgress.value = false
                    Log.d(TAG, "User cannot be created")
                }
            }
            .addOnFailureListener { e ->
                signUpProgress.value = false
                Log.d(TAG, "Inside onFailure")
                Log.d(TAG, "Error message: ${e.message}")
            }
    }

    private suspend fun sendEmailVerificationAndAwait(firebaseUser: FirebaseUser?): Boolean {
         return try {
                Log.d(TAG, "Inside email verification")
                while (!firebaseUser!!.isEmailVerified) {
                    firebaseUser.reload()
                    delay(2000)
                }
                true
            } catch (e: Exception) {
                false
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
