import com.jshme.buildsrc.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    compileSdkVersion(Dependencies.compileSdkVersion)
    buildToolsVersion(Dependencies.buildToolsVersion)

    defaultConfig {
        applicationId("com.jshme.kspsample")
        minSdkVersion(Dependencies.minSdkVersion)
        targetSdkVersion(Dependencies.targetSdkVersion)
        versionCode(1)
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("debug") {
            sourceSets {
                getByName("main") {
                    java.srcDir(File("build/generated/ksp/debug/kotlin"))
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(":ksp"))
    ksp(project(":ksp"))

    Dependencies.Kotlin.run {
        implementation(kotlinStdLib)
        implementation(reflect)
    }

    Dependencies.AndroidX.run {
        implementation(coreKtx)
        implementation(appcompat)
        implementation(constraintLayout)
    }

    Dependencies.Google.run {
        implementation(material)
    }

    Dependencies.Test.run {
        testImplementation(junit)
    }

    Dependencies.AndroidTest.run {
        androidTestImplementation(junit)
        androidTestImplementation(espresso)
    }


}

ksp {
    arg("projectName", project.name)
}
