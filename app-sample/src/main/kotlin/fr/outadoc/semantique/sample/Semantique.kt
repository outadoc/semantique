package fr.outadoc.semantique.sample

import fr.outadoc.semantique.api.cemantix.CemantixApiImpl
import fr.outadoc.semantique.api.cemantix.CemantixServer
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = CemantixApiImpl(
        CemantixServer(
            HttpClient(CIO) {
                install(JsonFeature)
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }
        )
    )

    println(api.getScore("continent"))
    println(api.getStats("continent"))
    println(api.getNearby("continent"))
}
