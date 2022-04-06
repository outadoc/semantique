package fr.outadoc.semantique.api.cemantix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsResponse(
    @SerialName("num")
    val rank: Long,
    @SerialName("solvers")
    val solvers: Long
)
