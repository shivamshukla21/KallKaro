package com.example.kallkaro.Data.HomeScreen

import com.example.kallkaro.Data.Login.LoginUIEvents

sealed class HomeUIEvents {
    data class NameChanged(val name: String) : HomeUIEvents()
    object LogoutButtonClicked : HomeUIEvents()
    object DeleteButtonClicked : HomeUIEvents()
    object ConnectButtonClicked : HomeUIEvents()
}