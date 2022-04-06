package fr.outadoc.cemantix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayStatsResponse(
    @SerialName("num")
    val dayNumber: Long,
    @SerialName("solvers")
    val solverCount: Long
)
