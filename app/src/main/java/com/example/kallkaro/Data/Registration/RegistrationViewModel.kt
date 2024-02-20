package com.example.kallkaro.Data.Registration

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
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
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.grpc.internal.SharedResourceHolder.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
        }
    }

    private suspend fun regclick(){
        Log.d(TAG, "Inside Signup")
        createUser(fname = registrationUIState.value.firstName, lname = registrationUIState.value.lastName, email = registrationUIState.value.email, password = registrationUIState.value.password)
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

    private suspend fun createUser(fname: String, lname: String, email: String, password: String) {
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
                                val user = hashMapOf("firstname" to fname, "lastname" to lname, "email" to email)
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

    fun handleGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            try {
                // Get a Firebase credential from the Google ID token
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val auth = FirebaseAuth.getInstance()

                // Sign in with Firebase using the Firebase credential
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        updateUI(null)
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                updateUI(null)
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
//        val context = LocalContext.current
        if (user != null) {
            // User is signed in, navigate to HomeScreen
            Router.navigateTo(Screen.HomeScreen)
        } else {
            // Sign-in failed, show a toast message
//            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Failed google sign in")
        }
    }

}
