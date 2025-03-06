package com.example.citationeapp.data.remote.dto

data class CitationLightDto(
    val id: Int,
    val quoteVO: String,
    val quoteVF: String,
    val difficulty: Int,
    val kind: String
)
