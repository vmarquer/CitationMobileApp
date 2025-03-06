package com.example.citationeapp.data.models

data class Citation(
    val id: Int,
    val quoteVO: String,
    val quoteVF: String,
    val movieVO: String? = null,
    val movieVF: String? = null,
    val userGuessMovieVO: String? = null,
    val userGuessMovieVF: String? = null,
    val caracter: String? = null,
    val actor: String? = null,
    val difficulty: Int,
    val kind: String,
    val result: Boolean? = null
)