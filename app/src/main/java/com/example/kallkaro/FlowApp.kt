package com.example.kallkaro

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FlowApp : Application() {
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
    }
}