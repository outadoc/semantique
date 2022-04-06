package fr.outadoc.cemantix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreResponse(
    @SerialName("num")
    val rank: Long,
    @SerialName("solvers")
    val solvers: Long,
    @SerialName("percentile")
    val percentile: Int? = null,
    @SerialName("score")
    val score: Float? = null,
    @SerialName("error")
    val error: String? = null
)
