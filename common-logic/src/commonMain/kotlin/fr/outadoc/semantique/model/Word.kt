package fr.outadoc.semantique.model

import fr.outadoc.semantique.api.model.WordScore
import fr.outadoc.semantique.storage.schema.Attempt

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

fun Attempt.toWord(): Word =
    Word(
        attemptNumber = attemptNumber,
        word = word,
        score = score,
        percentile = percentile
    )

fun Word.toAttempt(dayNumber: Long): Attempt =
    Attempt(
        dayNumber = dayNumber,
        attemptNumber = attemptNumber,
        word = word,
        score = score,
        percentile = percentile
    )
