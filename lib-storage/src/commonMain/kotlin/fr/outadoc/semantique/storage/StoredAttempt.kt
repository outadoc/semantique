package fr.outadoc.semantique.storage

data class StoredAttempt(
    val dayNumber: Int,
    val attemptNumber: Int,
    val word: String,
    val score: Float,
    val percentile: Int?
)
