pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "semantique"

include(":common-ui")
include(":viewmodel")

include(":app-android")
include(":app-desktop")

include(":app-sample")

include(":lib-cemantix")
include(":lib-semantic-api")
include(":lib-semantic-api-cemantix")
