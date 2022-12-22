import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dev.neverstoplearning.githubbrowser"
    compileSdk = ProjectVersions.compileSdk

    defaultConfig {
        applicationId = "dev.neverstoplearning.githubbrowser"
        minSdk = ProjectVersions.minSdk
        targetSdk = ProjectVersions.targetSdk
        versionCode = ProjectVersions.versionMajor * 10000 +
                ProjectVersions.versionMinor * 1000 +
                ProjectVersions.versionPatch * 100 +
                ProjectVersions.versionBuild * 10
        versionName = "${ProjectVersions.versionMajor}." +
                "${ProjectVersions.versionMinor}." +
                "${ProjectVersions.versionPatch}." +
                "${ProjectVersions.versionBuild}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.Compose.compiler)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(projects.android.feature.home)
    implementation(projects.android.feature.repositorydetails)
    implementation(projects.android.theme)

    implementation(projects.xplat.database)

    implementation(Deps.AndroidX.Compose.activity)
    implementation(Deps.Compose.navigation)

    implementation(Deps.AndroidX.corektx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.Material.core)
    implementation(Deps.AndroidX.constraintlayout)

    implementation(Deps.Timber.core)

    testImplementation(Deps.JUnit.junit)
}