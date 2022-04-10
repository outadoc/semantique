package fr.outadoc.semantique.api

import fr.outadoc.semantique.api.model.WordScore
import fr.outadoc.semantique.api.model.DayStats

interface SemanticApi {

    /**
     * Get stats on today's puzzle.
     */
    suspend fun getDayStats(languageCode: String): DayStats

    /**
     * Gets the score of a given word relative to the target word.
     *
     * Throws an exception if the word is considered invalid.
     */
    suspend fun getScore(languageCode: String, word: String): WordScore

    /**
     * Gets the top 1000 words that were nearest to the target word.
     *
     * Throws an exception if the given word is not the target word.
     * @param word the target word.
     */
    suspend fun getNearby(languageCode: String, word: String): List<WordScore>
}
