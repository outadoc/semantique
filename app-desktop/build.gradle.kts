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

                implementation(projects.commonUi)
                implementation(projects.libStorage)
                implementation(projects.libSemanticApiCemantix)

                implementation(libs.logback)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.cio)
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
