package fr.outadoc.semantique.sample

import fr.outadoc.cemantix.CemantixServer
import fr.outadoc.cemantix.exception.CemantixApiException
import fr.outadoc.cemantix.exception.CemantixInvalidTargetWordException
import fr.outadoc.semantique.api.cemantix.CemantixSemanticApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = CemantixSemanticApi(
        CemantixServer(
            HttpClient(CIO) {
                install(JsonFeature)
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        )
    )

    println(api.getDayStats())

    println(api.getScore("saucisse"))
    println(api.getScore("continent"))

    try {
        println(api.getScore("sovjsdpofjvsdfv"))
    } catch (e: CemantixApiException) {
        e.printStackTrace()
    }

    println(api.getNearby("continental"))

    try {
        println(api.getNearby("fovsdpofvjsdf"))
    } catch (e: CemantixInvalidTargetWordException) {
        e.printStackTrace()
    }
}
