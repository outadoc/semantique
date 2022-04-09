package fr.outadoc.semantique.model

data class Word(
    val attemptNumber: Int?,
    val word: String,
    val score: Float,
    val percentile: Int?
)
