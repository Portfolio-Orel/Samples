package com.orels.samples.login.model

abstract class SignInStep {
    object ConfirmSignUp : SignInStep()
    object ConfirmSignInWithNewPassword : SignInStep()
    class Done(val user: User?) : SignInStep()
    object Error : SignInStep()
}