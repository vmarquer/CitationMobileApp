package com.example.citationeapp.data.remote.dto

data class CitationAnswerResponseDTO(
    val id: Int,
    val movieVO: String,
    val movieVF: String,
    val caracter: String,
    val actor: String,
    val result: Boolean
)
