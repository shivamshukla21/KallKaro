package com.example.kallkaro.Data

data class LoginUIState (
    var email : String = "",
    var password : String = "",

    var EmailErr : Boolean = false,
    var PswdErr : Boolean = false
)