package com.example.citationeapp.data.domain.mapper

import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.models.Film
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto

fun CitationLightDto.toCitation(): Citation {
    return Citation(
        id = id,
        quoteVO = quoteVO,
        quoteVF = quoteVF,
        difficulty = difficulty,
        kind = kind,
        choices = choices
    )
}

fun Citation.updateWithResponse(
    response: CitationAnswerResponseDTO,
    userGuessMovie: Film?
): Citation {
    return this.copy(
        caracter = response.caracter,
        actor = response.actor,
        answerId = response.answerId,
        result = response.result,
        userGuessMovieVO = userGuessMovie?.titleVO,
        userGuessMovieVF = userGuessMovie?.titleVF
    )
}


fun Citation.updateWithUserGuess(userGuessMovieVO: String, userGuessMovieVF: String): Citation {
    return this.copy(
        userGuessMovieVO = userGuessMovieVO,
        userGuessMovieVF = userGuessMovieVF,
    )
}

fun Citation.toCitationLightDto(): CitationLightDto {
    return CitationLightDto(
        id = id,
        quoteVO = quoteVO,
        quoteVF = quoteVF,
        difficulty = difficulty,
        kind = kind,
        choices = choices
    )
}

fun Citation.toCitationAnswerResponseDto(): CitationAnswerResponseDTO {
    return CitationAnswerResponseDTO(
        id = id,
        caracter = this.caracter ?: "",
        actor = this.actor ?: "",
        answerId = this.answerId ?: -1,
        result = this.result == true,
    )
}
