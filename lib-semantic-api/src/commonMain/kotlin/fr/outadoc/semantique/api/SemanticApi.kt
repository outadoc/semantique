package fr.outadoc.semantique.api

import fr.outadoc.semantique.api.model.WordScore
import fr.outadoc.semantique.api.model.DayStats

interface SemanticApi {
    suspend fun getDayStats(): DayStats
    suspend fun getScore(word: String): WordScore
    suspend fun getNearby(word: String): List<WordScore>
}
