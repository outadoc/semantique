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
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(project(":common-ui"))
                implementation(project(":lib-storage"))
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
        mainClass = "fr.outadoc.semantique.desktop.SemantiqueKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb,
                TargetFormat.Rpm
            )

            packageName = "Semantique"
            version = "1.0.0"
            packageVersion = "1.0.0"
            copyright = "© 2022 Baptiste Candellier"
            vendor = "Baptiste Candellier"

            licenseFile.set(
                project.rootDir.resolve("LICENSE")
            )

            windows {
                menuGroup = "Semantique"
                upgradeUuid = "c8c6dd5c-26c5-46d7-9247-f068bb3c35a1"
            }

            macOS {
                bundleID = "fr.outadoc.semantique"
                dockName = "Sémantique"
            }

            linux {
                menuGroup = "Game"
                appCategory = "games"
            }
        }
    }
}
