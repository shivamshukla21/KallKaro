package com.example.kallkaro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kallkaro.ui.theme.KallKaroTheme

class MainActivity : ComponentActivity() {
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
    }
}