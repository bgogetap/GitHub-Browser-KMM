import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

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
    ios {
        binaries {
                findTest(NativeBuildType.DEBUG)?.linkerOpts("-lsqlite3")
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.xplat.appmodels)
                api(projects.xplat.githubapi)
                api(projects.xplat.database)

                implementation(projects.xplat.logging)

                implementation(Deps.KotlinX.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(projects.xplat.database.testutil)

                implementation(Deps.Kotlin.coroutinestest)
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
        val iosMain by getting {
            dependencies {
                implementation(projects.xplat.database)
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
