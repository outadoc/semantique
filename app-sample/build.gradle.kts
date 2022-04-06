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

    implementation("io.ktor:ktor-client-logging:1.6.8")
}
