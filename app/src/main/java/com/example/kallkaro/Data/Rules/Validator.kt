package com.example.kallkaro.Data.Rules

object Validator {

    fun ValidateFName(fname: String): ValidationResult{
        return ValidationResult(
            (!fname.isNullOrEmpty() && fname.length>2)
        )
    }
    fun ValidateLName(lname: String): ValidationResult{
        return ValidationResult(
            (!lname.isNullOrEmpty() && lname.length>2)
        )
    }
    fun ValidateEmail(email: String): ValidationResult{
        return ValidationResult(
            (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        )
    }
    fun ValidatePswd(pswd: String): ValidationResult{
        return ValidationResult(
            (pswd.length>5)
        )
    }
    fun ValidateCheck(chk: Boolean): ValidationResult{
        return ValidationResult(
            chk
        )
    }
}

data class ValidationResult(
    val status: Boolean
)