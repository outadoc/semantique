plugins {
    id("org.jetbrains.compose") version "1.1.1"
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(projects.commonUi)
    implementation(projects.libStorage)
    implementation(projects.libSemanticApiCemantix)

    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
    implementation("io.ktor:ktor-client-serialization:1.6.8")
    implementation("io.ktor:ktor-client-cio:1.6.8")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "fr.outadoc.semantique.android"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
