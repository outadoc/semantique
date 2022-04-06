pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "semantique"


include(":app-android")
include(":app-desktop")
include(":common")
include("lib-semantic-api")
include("lib-semantic-cemantix")
include("common-ui")
include("app-sample")
