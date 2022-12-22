import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

group = "dev.neverstoplearning"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    ios {
        binaries.filterIsInstance(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java)
            .forEach {
                it.transitiveExport = true
                it.export(project(":xplat:appmodels"))
                it.export(project(":xplat:coroutines"))
                it.export(project(":xplat:feature:repositorydetails"))
                it.export(project(":xplat:feature:toprepositories"))
                it.export(project(":xplat:logging"))

                it.linkerOpts += "-lsqlite3"
            }
    }

    sourceSets {
        val iosMain by getting {
            dependencies {
                api(project(":xplat:appmodels"))
                api(project(":xplat:coroutines"))
                api(project(":xplat:feature:repositorydetails"))
                api(project(":xplat:feature:toprepositories"))
                api(project(":xplat:logging"))
            }
        }
    }

    cocoapods {
        summary = "GitHub Browser xplat lib"
        homepage = "https://neverstoplearning.dev"
        framework {
            baseName = "GitHubBrowserXplat"
            // Dynamic library required for SwiftUI preview. See https://github.com/cashapp/sqldelight/issues/1442
            isStatic = false
        }
    }
}
