plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "dev.neverstoplearning"
version = "1.0-SNAPSHOT"

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    ios()
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common", Versions.kotlin))
                implementation(kotlin("test-annotations-common", Versions.kotlin))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit", Versions.kotlin))
                implementation(Deps.JUnit.junit)
            }
        }
        val iosMain by getting
        val iosTest by getting
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
