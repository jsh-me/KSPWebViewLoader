plugins {
    kotlin("jvm") version "1.5.20" apply false
}

buildscript {
    val kotlin_version by extra("1.5.10")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.5.20-1.0.0-beta04")
        classpath(kotlin("gradle-plugin", version = "1.5.20"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
