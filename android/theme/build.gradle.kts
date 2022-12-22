import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ProjectVersions.compileSdk

    defaultConfig {
        minSdk = ProjectVersions.minSdk
        targetSdk = ProjectVersions.targetSdk
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
    api(platform(Deps.Compose.bom))
    api(Deps.Compose.ui)
    api(Deps.Compose.foundation)
    api(Deps.Compose.Material.core)
    debugApi(Deps.Compose.tooling)

    implementation(Deps.Dagger.core)
    implementation(Deps.Hilt.core)
    kapt(Deps.Hilt.compiler)
    kapt(Deps.AndroidX.Hilt.compiler)

    implementation(kotlin("stdlib-jdk8", Versions.kotlin))
}
