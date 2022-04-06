package fr.outadoc.semantique.api.model

data class Score(
    val rank: Long,
    val percentile: Int?,
    val score: Double,
    val solvers: Long
)
