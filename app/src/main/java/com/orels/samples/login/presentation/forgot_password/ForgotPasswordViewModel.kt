package com.orels.samples.login.presentation.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf (ForgotPasswordState())

    fun onForgotPassword(username: String) {
        state = state.copy(state = State.LoadingForgotPassword)
        viewModelScope.launch {
            delay(1500)
            state = state.copy(state = State.ResetPassword)
        }
    }

    fun onResetPassword(code: String, password: String, confirmPassword: String) {
        state = state.copy(state = State.LoadingResetPassword)
        viewModelScope.launch {
            delay(1500)
            state = state.copy(state = State.Done)
        }
    }
}