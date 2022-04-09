package fr.outadoc.semantique.model

import fr.outadoc.semantique.api.model.WordScore

data class Word(
    val attemptNumber: Int,
    val word: String,
    val score: Float,
    val percentile: Int?
)

fun WordScore.toWord(attemptNumber: Int): Word =
    Word(
        attemptNumber = attemptNumber,
        word = word,
        score = score,
        percentile = percentile
    )
