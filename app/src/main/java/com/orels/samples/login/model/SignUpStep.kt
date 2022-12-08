package com.orels.samples.login.model

abstract class SignUpStep {
    object ConfirmSignUpWithNewPassword : SignUpStep()
    class Done(val user: User?) : SignUpStep()
    object Error : SignUpStep()
}