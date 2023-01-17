plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "dev.neverstoplearning"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    ios()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.xplat.database)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.sqliteDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.nativeDriver)
            }
        }
    }
}

android {
    compileSdk = ProjectVersions.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = ProjectVersions.minSdk
        targetSdk = ProjectVersions.targetSdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
