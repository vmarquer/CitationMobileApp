package com.example.citationeapp.data.remote.dto

data class UtilisateurInfosDTO(
    val username: String,
    val email: String,
    val role: String,
    val answers: Int,
    val goodAnswers: Int
)