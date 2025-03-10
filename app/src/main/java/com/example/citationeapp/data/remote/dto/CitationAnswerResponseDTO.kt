package com.example.citationeapp.data.remote.dto

data class CitationAnswerResponseDTO(
    val id: Int,
    val caracter: String,
    val actor: String,
    val answerId: Int,
    val result: Boolean
)
