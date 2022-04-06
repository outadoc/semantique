package fr.outadoc.semantique.sample

import fr.outadoc.semantique.api.cemantix.CemantixApiException
import fr.outadoc.semantique.api.cemantix.CemantixApiImpl
import fr.outadoc.semantique.api.cemantix.CemantixInvalidTargetWordException
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
