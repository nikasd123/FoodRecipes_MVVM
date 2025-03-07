// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.8")
    }
}

plugins {
    id("com.android.application") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
}