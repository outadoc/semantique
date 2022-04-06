package fr.outadoc.semantique.api.model

class Score(
    val rank: Long,
    val percentile: Int?,
    val score: Double,
    val solvers: Long
)
