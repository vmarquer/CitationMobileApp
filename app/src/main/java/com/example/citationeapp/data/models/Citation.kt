package com.example.citationeapp.data.models

data class Citation(
    val id: Int,
    val quoteVO: String,
    val quoteVF: String,
    val userGuessMovieVO: String? = null,
    val userGuessMovieVF: String? = null,
    val caracter: String? = null,
    val actor: String? = null,
    val difficulty: Int,
    val kind: String,
    val answerId: Int? = null,
    val choices: List<Film>,
    val result: Boolean? = null
)

fun Citation.getAnswer(): Film ? {
    return choices.find { it.id == answerId }
}