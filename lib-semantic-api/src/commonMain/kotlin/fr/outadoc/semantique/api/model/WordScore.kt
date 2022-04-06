package fr.outadoc.semantique.api.model

data class WordScore(
    val word: String,
    val score: Float,
    val percentile: Int?,
    val dayStats: DayStats? = null
)
