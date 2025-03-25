package com.example.citationeapp.data.models

import androidx.compose.ui.graphics.Color
import com.example.citationeapp.ui.theme.fail
import com.example.citationeapp.ui.theme.success
import com.example.citationeapp.ui.theme.yellow

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

fun Citation.getDifficultyLabel(): String {
    return when (difficulty) {
        1 -> "Facile"
        2 -> "Moyen"
        3 -> "Difficile"
        else -> "Inconnue"
    }
}

fun Citation.getKindLabel(): String {
    return when (kind) {
        "movie" -> "Film"
        "serie" -> "Série"
        else -> "Inconnue"
    }
}

fun Citation.getDifficultyBackgroundColor(): Color {
    return when (difficulty) {
        1 -> success
        2 -> yellow
        3 -> fail
        else -> fail
    }
}