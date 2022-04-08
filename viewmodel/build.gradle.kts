plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":lib-semantic-api"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
