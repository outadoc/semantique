package fr.outadoc.cemantix

import fr.outadoc.cemantix.model.NearbyItem
import fr.outadoc.cemantix.model.ScoreResponse
import fr.outadoc.cemantix.model.StatsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class CemantixServer(private val client: HttpClient) {

    private companion object {
        val BASE_URL = Url("https://cemantix.herokuapp.com")
    }

    suspend fun getDayStats(): StatsResponse =
        client.get(BASE_URL.copy(encodedPath = "/stats"))

    suspend fun getScore(word: String): ScoreResponse =
        client.post(BASE_URL.copy(encodedPath = "/score")) {
            body = FormDataContent(
                Parameters.build {
                    append("word", word)
                }
            )
        }

    suspend fun getNearby(word: String): List<NearbyItem> =
        client.post(BASE_URL.copy(encodedPath = "/nearby")) {
            body = FormDataContent(
                Parameters.build {
                    append("word", word)
                }
            )
        }
}
