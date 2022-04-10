package fr.outadoc.cemantix

import fr.outadoc.cemantix.model.DayStatsResponse
import fr.outadoc.cemantix.model.NearbyItem
import fr.outadoc.cemantix.model.ScoreResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.util.*

class CemantixServer(private val client: HttpClient) {

    private fun getBaseUrl(languageCode: String) = when (languageCode) {
        Locale.FRENCH.language -> Url("https://cemantix.herokuapp.com")
        Locale.ENGLISH.language -> Url("https://cemantle.herokuapp.com")
        else -> error("Unsupported language code: $languageCode")
    }

    suspend fun getDayStats(languageCode: String): DayStatsResponse =
        client.get(getBaseUrl(languageCode).copy(encodedPath = "/stats"))

    suspend fun getScore(languageCode: String, word: String): ScoreResponse =
        client.post(getBaseUrl(languageCode).copy(encodedPath = "/score")) {
            body = FormDataContent(
                parametersOf("word", word)
            )
        }

    suspend fun getNearby(languageCode: String, word: String): List<NearbyItem> =
        client.post(getBaseUrl(languageCode).copy(encodedPath = "/nearby")) {
            body = FormDataContent(
                parametersOf("word", word)
            )
        }
}
