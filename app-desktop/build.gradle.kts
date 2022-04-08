import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.1.1"
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common-ui"))
                implementation(compose.desktop.currentOs)

                implementation(project(":lib-semantic-api-cemantix"))

                implementation("ch.qos.logback:logback-classic:1.2.11")
                implementation("io.ktor:ktor-client-logging:1.6.8")
                implementation("io.ktor:ktor-client-serialization:1.6.8")
                implementation("io.ktor:ktor-client-cio:1.6.8")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jvm"
            packageVersion = "1.0.0"
        }
    }
}
