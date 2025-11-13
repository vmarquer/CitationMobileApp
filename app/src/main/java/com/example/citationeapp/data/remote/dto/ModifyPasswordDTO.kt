package com.example.citationeapp.data.remote.dto

data class ModifyPasswordDTO(
    val email: String,
    val oldPassword: String,
    val newPassword: String
) {}