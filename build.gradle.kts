buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }
}

plugins {
    kotlin("plugin.serialization") version "1.6.10" apply false
    kotlin("multiplatform") version "1.6.10" apply false
}

allprojects {
    group = "fr.outadoc"
    version = "1.0"

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
