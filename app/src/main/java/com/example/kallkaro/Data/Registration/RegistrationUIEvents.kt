package com.example.kallkaro.Data.Registration

sealed class RegistrationUIEvents {
    data class FirstNameChanged(val firstName: String) : RegistrationUIEvents()
    data class LastNameChanged(val lastName: String) : RegistrationUIEvents()
    data class EmailChanged(val email: String) : RegistrationUIEvents()
    data class PasswordChanged(val password: String) : RegistrationUIEvents()
    data class CheckBoxClicked(val checked: Boolean) : RegistrationUIEvents()

    object RegistrationButtonClicked : RegistrationUIEvents()
    object LogoutButtonClicked : RegistrationUIEvents()
}