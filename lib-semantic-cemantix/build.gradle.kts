plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "fr.outadoc"
version = "1.0"

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":lib-semantic-api"))
                api("io.ktor:ktor-client-serialization:1.6.8")

                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:1.6.8")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api("io.ktor:ktor-client-cio:1.6.8")
            }
        }
    }
}
