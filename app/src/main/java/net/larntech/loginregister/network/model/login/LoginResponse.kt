package net.larntech.loginregister.network.model.login

data class LoginResponse(
    var status: Boolean,
    var message: String,
    var id: String,
    var username: String
): java.io.Serializable
