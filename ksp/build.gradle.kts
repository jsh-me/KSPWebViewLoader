import com.jshme.buildsrc.Dependencies

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    google()
}

dependencies {

    Dependencies.Kotlin.run {
        implementation(kotlinStdLib)
    }

    Dependencies.Ksp.run {
        implementation(kspApi)
    }
}
