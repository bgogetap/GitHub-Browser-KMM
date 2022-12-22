plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

group = "dev.neverstoplearning"
version = "1.0-SNAPSHOT"

sqldelight {
    database("GitHubBrowserDatabase") {
        packageName = "dev.neverstoplearning.githubbrowser.xplat.database"
    }
}

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
                implementation(projects.xplat.logging)

                api(Deps.Kotlin.coroutinescore)
                implementation(Deps.SqlDelight.runtime)
                implementation(Deps.SqlDelight.coroutinescore)
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
                implementation(Deps.SqlDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit", Versions.kotlin))
                implementation(Deps.JUnit.junit)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.SqlDelight.nativeDriver)
            }
        }
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
