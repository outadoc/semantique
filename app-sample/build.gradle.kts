plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("fr.outadoc.semantique.sample.SemantiqueKt")
}

dependencies {
    implementation(project(":lib-semantic-api-cemantix"))

    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-client-logging:1.6.8")
    implementation("io.ktor:ktor-client-serialization:1.6.8")
    implementation("io.ktor:ktor-client-cio:1.6.8")
}
