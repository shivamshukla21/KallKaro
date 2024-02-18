package com.example.kallkaro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.ui.theme.KallKaroTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        viewModel._toastMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}