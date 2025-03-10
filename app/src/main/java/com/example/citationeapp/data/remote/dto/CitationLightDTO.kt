package com.example.citationeapp.data.remote.dto

import com.example.citationeapp.data.models.Film

data class CitationLightDto(
    val id: Int,
    val quoteVO: String,
    val quoteVF: String,
    val difficulty: Int,
    val kind: String,
    val choices: List<Film>
)
