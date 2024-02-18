package com.example.kallkaro.Data.Login

sealed class LoginUIEvents {
    data class EmailChanged(val email: String) : LoginUIEvents()
    data class PasswordChanged(val password: String) : LoginUIEvents()

    object LoginButtonClicked : LoginUIEvents()
    object ResetClicked : LoginUIEvents()
}