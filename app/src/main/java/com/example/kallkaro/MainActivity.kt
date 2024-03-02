package com.example.kallkaro

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kallkaro.Data.Login.LoginViewModel
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Router.updateScreenBasedOnAuthStatus
import com.example.kallkaro.ui.theme.KallKaroTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var regviewModel: RegistrationViewModel
    private lateinit var logviewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logviewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        enableEdgeToEdge()
        setContent {
            KallKaroTheme {
                Surface (modifier = Modifier
                    .fillMaxSize()){
                    Scaffold(modifier = Modifier.fillMaxSize()) {it
                       KallKaro()
                    }
                }
            }
        }
        checkAuthenticationStatus()
        logviewModel.toastMessage.observe(this, Observer { toastMessage ->
            Log.d("LoginViewModel in main", "Thread: ${Thread.currentThread().name}")
            Log.d("MainActivity", "Toast Message: $toastMessage")
            Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_SHORT).show()
        })
//        regviewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
//        regviewModel._toastMessage.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        })
    }
    private fun checkAuthenticationStatus() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val isAuthenticated = currentUser != null && currentUser.isEmailVerified
        updateScreenBasedOnAuthStatus(isAuthenticated)
    }
}