buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }
}

plugins {
    kotlin("plugin.serialization") version "1.6.10" apply false
    kotlin("multiplatform") version "1.6.10" apply false
    id("org.jetbrains.compose") version "1.1.1" apply false
    id("dev.icerock.mobile.multiplatform-resources") version "0.19.0" apply false
}

allprojects {
    group = "fr.outadoc"
    version = properties["fr.outadoc.semantique.versionName"] as String
    ext["versionCode"] = (properties["fr.outadoc.semantique.versionCode"] as String).toInt()

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
