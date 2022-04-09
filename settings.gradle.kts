pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "semantique"

include(":app-android")
include(":app-desktop")

include(":common-ui")
include(":common-logic")

include(":lib-storage")
include(":lib-cemantix")
include(":lib-semantic-api")
include(":lib-semantic-api-cemantix")
