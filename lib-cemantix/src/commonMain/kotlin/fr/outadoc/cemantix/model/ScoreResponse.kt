package fr.outadoc.cemantix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreResponse(
    @SerialName("num")
    val dayNumber: Long,
    @SerialName("solvers")
    val solverCount: Long? = null,
    @SerialName("percentile")
    val percentile: Int? = null,
    @SerialName("score")
    val score: Float? = null,
    @SerialName("error")
    val error: String? = null
)
