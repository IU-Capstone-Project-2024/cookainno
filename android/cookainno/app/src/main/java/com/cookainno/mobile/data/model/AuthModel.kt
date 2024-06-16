package com.cookainno.mobile.data.model

data class RegistrationRequest(val username: String, val email: String, val password: String)
data class ConfirmationRequest(val email: String, val confirmationCode: String)
data class LoginRequest(val username: String, val password: String)
data class RegistrationResponse(val token: String?)
data class LoginResponse(val success: Boolean, val message: String, val token: String?)
