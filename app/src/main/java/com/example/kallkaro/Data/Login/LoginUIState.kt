package com.example.kallkaro.Data.Login

data class LoginUIState (
    var email : String = "",
    var password : String = "",

    var EmailErr : Boolean = false,
    var PswdErr : Boolean = false
)