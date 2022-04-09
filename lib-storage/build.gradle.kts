plugins {
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
}

group = "fr.outadoc"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
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
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
            }
        }
    }
}
