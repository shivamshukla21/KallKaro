package com.example.kallkaro.Data.HomeScreen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    private val TAG = HomeScreenViewModel::class.simpleName
    var homeUIState = mutableStateOf(HomeUIState())
    var homeProgress = mutableStateOf(false)

    fun onEvent(event: HomeUIEvents) {
        printstate()
        when (event) {
            is HomeUIEvents.NameChanged -> {
                Log.d(TAG, "Name changing")
                homeUIState.value = homeUIState.value.copy(
                name = event.name
                )
                printstate()
            }
            is HomeUIEvents.DeleteButtonClicked -> {
                deleteclick()
            }
            is HomeUIEvents.LogoutButtonClicked -> {
                logoutclick()
            }
            is HomeUIEvents.ConnectButtonClicked -> {
                connectclick()
            }
        }
    }

    private fun printstate() {
        Log.d(TAG, "Inside login printstate")
        Log.d(TAG, homeUIState.value.toString())
    }
    private fun logoutclick(){
        Log.d(TAG, "Inside logout")
        logoutUser()
    }
    private fun deleteclick(){
        Log.d(TAG, "Inside delete")
        deleteFirebaseUser()
    }
    private fun connectclick(){
        Log.d(TAG, "Inside connect")
        connectuser()
    }
    private fun connectuser(){
        Log.d(TAG, "INside cnnnct fx")
    }

    private fun logoutUser(){
        homeProgress.value = true
        val auth = FirebaseAuth.getInstance()
        val authlistener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Logged Out Successful")
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    Router.navigateTo(Screen.LoginScreen)
                    homeProgress.value = false
                }
            } else {
                Log.d(TAG, "Logout Fail")
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    homeProgress.value = false
                }
            }
        }
        auth.addAuthStateListener(authlistener)
        auth.signOut()
    }

    fun deleteFirebaseUser() {
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        homeProgress.value = true
        Log.d("Dekho", "${user?.email}")

        user?.let { firebaseUser ->
            // Get the list of providers linked to the user's account
            val providers = firebaseUser.providerData.map { it.providerId }

            // Check if the user is logged in with email or Google
            val signInMethod = providers.find { it == EmailAuthProvider.PROVIDER_ID || it == GoogleAuthProvider.PROVIDER_ID }

            signInMethod?.let {
                // User is signed in with email or Google, proceed with deletion
                // Delete user data from Firestore
                db.collection("users").document(firebaseUser.email.toString())
                    .delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "User data deleted from Firestore")
                        // Delete the auth account
                        firebaseUser.delete().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(1000)
                                    homeProgress.value = false
                                    Router.navigateTo(Screen.LoginScreen)
                                }
                                Log.d(TAG, "User account deleted successfully.")
                            } else {
                                homeProgress.value = false
                                Log.d(TAG, "Failed to delete user account.")
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error deleting user data: $e")
                        homeProgress.value = false
                    }
            } ?: run {
                // User is signed in with a provider other than email or Google
                Log.d(TAG, "User is signed in with providers: $providers. Cannot delete account.")
                homeProgress.value = false
            }
        }
    }
}