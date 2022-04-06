package fr.outadoc.semantique.api.cemantix.model

import fr.outadoc.semantique.api.cemantix.NearbyItemSerializer
import kotlinx.serialization.Serializable

@Serializable(with = NearbyItemSerializer::class)
data class NearbyItem(
    val word: String,
    val percentile: Int,
    val score: Double
)
