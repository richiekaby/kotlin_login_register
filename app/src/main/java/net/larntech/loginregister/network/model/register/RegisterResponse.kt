package net.larntech.loginregister.network.model.register
data class RegisterResponse(
    var status: Boolean,
    var message: String,
    var id: String,
    var username: String
)