plugins {
    kotlin("jvm")
    application
}

group = "fr.outadoc"
version = "1.0"

application {
    mainClass.set("fr.outadoc.semantique.sample.SemantiqueKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":lib-semantic-cemantix"))

    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-client-logging:1.6.8")
}
