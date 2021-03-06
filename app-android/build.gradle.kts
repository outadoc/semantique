plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(projects.commonUi)
    implementation(projects.libStorage)
    implementation(projects.libSemanticApiCemantix)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.cio)
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "fr.outadoc.semantique.android"
        minSdk = 24
        targetSdk = 31
        versionCode = project.ext["versionCode"] as Int
        versionName = version as String
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
