package com.orels.samples.login.model

data class User(
    var userId: String = "",
    var token: String? = null,
    val username: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null
) {
    companion object {
        val AUTHORIZED = User(
            userId = "1",
            token = "token",
            username = "username",
            email = "email",
            firstName = "firstName",
            lastName = "lastName",

        )

        val UNAUTHORIZED = null
    }
}