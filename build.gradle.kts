plugins {
    id("com.android.application") version "8.12.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10" apply false
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.9.3" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false // ✅ Needed for Compose with Kotlin 2.x
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.3") // latest version
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
