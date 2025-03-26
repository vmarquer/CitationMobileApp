package com.example.citationeapp.data.models

import androidx.compose.ui.graphics.Color
import com.example.citationeapp.R
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

fun Citation.getDifficultyLabel(): Int {
    return when (difficulty) {
        1 -> R.string.play_difficulty_easy
        2 -> R.string.play_difficulty_medium
        3 -> R.string.play_difficulty_difficult
        else -> R.string.play_difficulty_easy
    }
}

fun Citation.getKindLabel(): Int {
    return when (kind) {
        "movie" -> R.string.play_kind_movie
        "serie" -> R.string.play_kind_serie
        else -> R.string.play_kind_movie
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