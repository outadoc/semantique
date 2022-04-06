package fr.outadoc.semantique.api.cemantix

import fr.outadoc.cemantix.CemantixServer
import fr.outadoc.cemantix.exception.CemantixApiException
import fr.outadoc.cemantix.exception.CemantixInvalidTargetWordException
import fr.outadoc.cemantix.model.*
import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.NearbyWord
import fr.outadoc.semantique.api.model.Score
import fr.outadoc.semantique.api.model.Stats
import io.ktor.client.call.*

class CemantixSemanticApi(private val cemantixServer: CemantixServer) : SemanticApi {

    override suspend fun getDayStats(): Stats =
        cemantixServer.getDayStats().toStats()

    override suspend fun getScore(word: String): Score =
        cemantixServer.getScore(word).toScore()

    override suspend fun getNearby(word: String): List<NearbyWord> =
        try {
            cemantixServer.getNearby(word).map { nearby -> nearby.toNearbyWord() }
        } catch (e: NoTransformationFoundException) {
            throw CemantixInvalidTargetWordException(word)
        }

    private fun StatsResponse.toStats(): Stats =
        Stats(rank = rank, solvers = solvers)

    private fun ScoreResponse.toScore(): Score =
        error?.let { error -> throw CemantixApiException(error) }
            ?: Score(
                rank = rank,
                percentile = percentile,
                score = score!!,
                solvers = solvers
            )

    private fun NearbyItem.toNearbyWord(): NearbyWord =
        NearbyWord(word = word, percentile = percentile, score = score)
}
