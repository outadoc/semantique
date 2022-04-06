package fr.outadoc.semantique.api.cemantix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreResponse(
    @SerialName("num")
    val rank: Long,
    @SerialName("percentile")
    val percentile: Int?,
    @SerialName("score")
    val score: Double,
    @SerialName("solvers")
    val solvers: Long
)
