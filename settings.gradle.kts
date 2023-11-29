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

include(":android-core")
project(":android-core").projectDir = File("android/android-core")

include(":latestlistings")
project(":latestlistings").projectDir = File("android/latestlistings")
