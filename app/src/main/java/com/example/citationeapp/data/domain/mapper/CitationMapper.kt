package com.example.citationeapp.data.domain.mapper

import com.example.citationeapp.data.models.Citation
import com.example.citationeapp.data.remote.dto.CitationAnswerRequestDTO
import com.example.citationeapp.data.remote.dto.CitationAnswerResponseDTO
import com.example.citationeapp.data.remote.dto.CitationLightDto

fun CitationLightDto.toCitation(): Citation {
    return Citation(
        id = id,
        quoteVO = quoteVO,
        quoteVF = quoteVF,
        movieVO = null,
        movieVF = null,
        caracter = null,
        actor = null,
        difficulty = difficulty,
        kind = kind,
        result = null
    )
}

fun Citation.updateWithResponse(response: CitationAnswerResponseDTO): Citation {
    return this.copy(
        movieVO = response.movieVO,
        movieVF = response.movieVF,
        caracter = response.caracter,
        actor = response.actor,
        result = response.result
    )
}

fun Citation.updateWithUserGuess(response: CitationAnswerRequestDTO): Citation {
    return this.copy(
        userGuessMovieVO = response.movieVO,
        userGuessMovieVF = response.movieVF,
    )
}

fun Citation.toCitationLightDto(): CitationLightDto {
    return CitationLightDto(
        id = id,
        quoteVO = quoteVO,
        quoteVF = quoteVF,
        difficulty = difficulty,
        kind = kind,
    )
}

fun Citation.toCitationAnswerResponseDto(): CitationAnswerResponseDTO {
    return CitationAnswerResponseDTO(
        id = id,
        movieVO = this.movieVO?: "",
        movieVF = this.movieVF?: "",
        caracter = this.caracter?: "",
        actor = this.caracter?: "",
        result = this.result?: false,
    )
}
