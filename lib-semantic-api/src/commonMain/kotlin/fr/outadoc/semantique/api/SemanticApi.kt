package fr.outadoc.semantique.api

import fr.outadoc.semantique.api.model.NearbyWord
import fr.outadoc.semantique.api.model.Score
import fr.outadoc.semantique.api.model.Stats

interface SemanticApi {
    suspend fun getStats(word: String): Stats
    suspend fun getScore(word: String): Score
    suspend fun getNearby(word: String): List<NearbyWord>
}
