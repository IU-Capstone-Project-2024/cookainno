package com.cookainno.mobile.data.model

data class RegistrationRequest(val username: String, val email: String, val password: String)
data class ConfirmationRequest(val email: String, val confirmationCode: String)
data class LoginRequest(val username: String, val password: String)
data class RegistrationResponse(val token: String?)
data class LoginResponse(val success: Boolean, val message: String, val token: String?, val userId: Int?)
data class UserDataRequest(val user_id: Int, val height: Int, val weight: Int, val date_of_birth: String)
