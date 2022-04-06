package fr.outadoc.cemantix.model

import fr.outadoc.cemantix.NearbyItemSerializer
import kotlinx.serialization.Serializable

@Serializable(with = NearbyItemSerializer::class)
data class NearbyItem(
    val word: String,
    val percentile: Int,
    val score: Float
)
