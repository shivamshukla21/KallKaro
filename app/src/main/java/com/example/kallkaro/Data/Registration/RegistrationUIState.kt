package com.example.kallkaro.Data.Registration

import com.google.firebase.auth.AuthResult

data class RegistrationUIState (
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var password : String = "",
    var checked : Boolean = false,

    var fNameErr : Boolean = false,
    var lNameErr : Boolean = false,
    var EmailErr : Boolean = false,
    var PswdErr : Boolean = false,
    var chkErr : Boolean = false
)