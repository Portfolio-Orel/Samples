package com.orels.samples.login.presentation.forgot_password

import androidx.annotation.StringRes

data class ForgotPasswordState(
    val state: State = State.ForgotPassword,

    val isLoading: Boolean = false,
    @StringRes val error: Int? = null,
)

enum class State {
    ForgotPassword,
    LoadingForgotPassword,
    ResetPassword,
    LoadingResetPassword,
    Done,
    Error
}