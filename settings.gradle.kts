pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}

include(":app", ":shared")
rootProject.name = "CryptoCoin"

enableFeaturePreview("VERSION_CATALOGS")
include(":android-core")
project(":android-core").projectDir = File("android/android-core")
