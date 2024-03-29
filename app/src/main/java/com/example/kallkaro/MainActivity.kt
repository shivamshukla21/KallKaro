package com.example.kallkaro

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.flow.*
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kallkaro.Components.SignInButton
import com.example.kallkaro.Data.Login.LoginViewModel
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Router.updateScreenBasedOnAuthStatus
import com.example.kallkaro.ui.theme.KallKaroTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var logviewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logviewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logviewModel.toastMessage.collect {
                    Log.d("TAG", "toast ki value ab: $it")
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
    }

    private fun checkAuthenticationStatus() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val isAuthenticated = currentUser != null && currentUser.isEmailVerified
        updateScreenBasedOnAuthStatus(isAuthenticated)
    }
    private val viewModel = RegistrationViewModel()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == viewModel.getRcSignIn()) {
            viewModel.handleSignInResult(data)
        }
    }

}