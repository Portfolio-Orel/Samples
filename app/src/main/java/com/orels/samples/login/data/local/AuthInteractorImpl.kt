package com.orels.samples.login.data.local

import com.orels.samples.login.model.ResetPasswordStep
import com.orels.samples.login.model.SignInStep
import com.orels.samples.login.model.SignUpStep
import com.orels.samples.login.model.User
import com.orels.samples.login.model.interactor.AuthInteractor
import javax.inject.Inject

class AuthInteractorImpl @Inject constructor(): AuthInteractor {
    override suspend fun initialize(configFileResourceId: Int) = Unit

    override suspend fun login(username: String, password: String): SignInStep = SignInStep.Done(User.AUTHORIZED)

    override suspend fun logout() = Unit

    override suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
    ): SignUpStep = SignUpStep.ConfirmSignUpWithNewPassword

    override suspend fun confirmUser(username: String, password: String, code: String): SignUpStep = SignUpStep.Done(User.AUTHORIZED)

    override suspend fun forgotPassword(username: String): ResetPasswordStep = ResetPasswordStep.ConfirmSignUpWithNewPassword

    override suspend fun resetPassword(username: String, code: String, newPassword: String) = Unit

    override suspend fun getToken(): String? = User.AUTHORIZED.token

    override suspend fun refreshToken(): String? = User.AUTHORIZED.token

    @Suppress("RedundantNullableReturnType")
    override suspend fun getUser(): User? = User.AUTHORIZED
}