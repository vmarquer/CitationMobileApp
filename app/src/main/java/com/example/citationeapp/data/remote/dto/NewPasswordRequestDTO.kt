package com.example.citationeapp.data.remote.dto

data class NewPasswordRequestDTO(
    val email: String,
    val activationCode: String,
    val password: String
)