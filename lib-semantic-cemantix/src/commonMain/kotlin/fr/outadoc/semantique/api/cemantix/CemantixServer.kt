package fr.outadoc.semantique.api.cemantix

import fr.outadoc.semantique.api.cemantix.model.ScoreResponse
import fr.outadoc.semantique.api.cemantix.model.StatsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class CemantixServer(private val client: HttpClient) {

    private companion object {
        val BASE_URL = Url("https://cemantix.herokuapp.com")
    }

    suspend fun getStats(word: String): StatsResponse {
        return client.get(BASE_URL.copy(encodedPath = "/stats")) {
            parameter("word", word)
        }
    }

    suspend fun getScore(word: String): ScoreResponse {
        return client.post(BASE_URL.copy(encodedPath = "/score")) {
            body = FormDataContent(
                Parameters.build {
                    append("word", word)
                }
            )
        }
    }

    suspend fun getNearby(word: String): List<List<Any>> {
        return client.get(BASE_URL.copy(encodedPath = "/nearby")) {
            parameter("word", word)
        }
    }
}
