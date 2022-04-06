package fr.outadoc.semantique.api.model

data class NearbyWord(
    val word: String,
    val percentile: Int?,
    val score: Float
)
