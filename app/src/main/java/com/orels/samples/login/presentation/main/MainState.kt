package com.orels.samples.login.presentation.main

import androidx.annotation.StringRes
import com.orels.samples.login.model.ResetPasswordStep
import com.orels.samples.login.model.SignInStep
import com.orels.samples.login.model.SignUpStep

data class LoginState(
    val signInStep: SignInStep? = null,
    val signUpStep: SignUpStep? = null,
    val resetPasswordStep: ResetPasswordStep? = null,

    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,

    val authState: AuthState = AuthState.SignedOut

)

enum class AuthState {
    SignedOut,
    SignedIn
}