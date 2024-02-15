package com.example.kallkaro.Data

sealed class LoginUIEvents {
    data class EmailChanged(val email: String) : LoginUIEvents()
    data class PasswordChanged(val password: String) : LoginUIEvents()

    object LoginButtonClicked : LoginUIEvents()
}