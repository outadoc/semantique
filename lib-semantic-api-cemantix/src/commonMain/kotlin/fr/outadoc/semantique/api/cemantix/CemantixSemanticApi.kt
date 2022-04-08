package fr.outadoc.semantique.api.cemantix

import fr.outadoc.cemantix.CemantixServer
import fr.outadoc.cemantix.exception.CemantixApiException
import fr.outadoc.cemantix.exception.CemantixInvalidTargetWordException
import fr.outadoc.cemantix.model.*
import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.WordScore
import fr.outadoc.semantique.api.model.DayStats
import io.ktor.client.call.*

class CemantixSemanticApi(private val cemantixServer: CemantixServer) : SemanticApi {

    override suspend fun getDayStats(): DayStats =
        cemantixServer.getDayStats().toStats()

    override suspend fun getScore(word: String): WordScore =
        cemantixServer.getScore(word).toScore(word)

    override suspend fun getNearby(word: String): List<WordScore> =
        try {
            cemantixServer.getNearby(word).map { nearby -> nearby.toScore() }
        } catch (e: NoTransformationFoundException) {
            throw CemantixInvalidTargetWordException(word)
        }

    private fun DayStatsResponse.toStats(): DayStats =
        DayStats(dayNumber = dayNumber, solverCount = solverCount)

    private fun ScoreResponse.toScore(word: String): WordScore =
        error?.let { error -> throw CemantixApiException(error) }
            ?: WordScore(
                word = word,
                score = score!!,
                percentile = percentile,
                dayStats = DayStats(
                    dayNumber = dayNumber,
                    solverCount = solverCount!!
                )
            )

    private fun NearbyItem.toScore(): WordScore =
        WordScore(
            word = word,
            percentile = percentile,
            score = score
        )
}
