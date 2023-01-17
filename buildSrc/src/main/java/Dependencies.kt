object ProjectVersions {
    const val compileSdk = 33
    const val minSdk = 29
    const val targetSdk = 33

    const val versionMajor = 0
    const val versionMinor = 1
    const val versionPatch = 0
    const val versionBuild = 0
}

object Versions {
    const val coroutines = "1.4.2"
    const val dagger = "_"
    const val daggerhilt = "_"
    const val googleauth = "17.0.0"
    const val googletruth = "1.0"
    const val junit = "4.12"
    const val kotlin = "_"
    const val mockito = "3.2.4"
    const val robolectric = "4.3.1"

    object AndroidX {
        const val core = "1.1.0"
        const val security = "1.0.0-beta01"
    }
}

object Deps {

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:_"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:_"
        const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta01"
        const val corektx = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val recyclerview = "androidx.recyclerview:recyclerview:_"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Compose {
            const val activity = "androidx.activity:activity-compose:_"
        }

        object DataStore {
            const val prefs = "androidx.datastore:datastore-preferences:_"
        }

        object Fragment {
            const val fragment = "androidx.fragment:fragment:_"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:_"
        }

        object Hilt {
            const val work = "androidx.hilt:hilt-work:_"
            const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:_"
            const val navigation = "androidx.hilt:hilt-navigation-fragment:_"
            const val compiler = "androidx.hilt:hilt-compiler:_"
        }

        object Lifecycle {
            // ViewModel and LiveData
            const val livedata =
                "androidx.lifecycle:lifecycle-livedata-ktx:_"
            const val viewmodel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:_"
            const val extensions =
                "androidx.lifecycle:lifecycle-extensions:_"
            const val compose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

            // For use with kapt
            const val compiler =
                "androidx.lifecycle:lifecycle-common-java8:_"
            const val testing = "androidx.arch.core:core-testing:_"
        }

        object Navigation {
            private const val version = "2.4.1"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object WorkManager {
            private const val version = "2.4.0"
            const val runtime = "androidx.work:work-runtime-ktx:$version"
            const val testing = "androidx.work:work-testing:$version"
        }

        const val security = "androidx.security:security-crypto:${Versions.AndroidX.security}"
    }

    object Coil {
        const val core = "io.coil-kt:coil:_"
        const val compose = "io.coil-kt:coil-compose:_"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:2022.11.00"

        const val animation = "androidx.compose.animation:animation"
        const val foundation = "androidx.compose.foundation:foundation"
        const val layout = "androidx.compose.foundation:foundation-layout"
        const val livedata = "androidx.compose.runtime:runtime-livedata"
        const val runtime = "androidx.compose.runtime:runtime"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val ui = "androidx.compose.ui:ui"
        const val uiUtil = "androidx.compose.ui:ui-util"
        const val uiTest = "androidx.compose.ui:ui-test-junit4"

        const val compiler = "androidx.compose.compiler:compiler:_"
        const val testing = "androidx.ui:ui-test:_"
        const val navigation = "androidx.navigation:navigation-compose:_"

        object Material {
            const val core = "androidx.compose.material3:material3:_"
            const val icons = "androidx.compose.material:material-icons-core:_"
            const val iconsextended = "androidx.compose.material:material-icons-extended:_"
        }
    }

    object Exposed {
        private const val version = "0.26.1"

        const val core = "org.jetbrains.exposed:exposed-core:$version"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:$version"
    }

    object GoogleMaps {
        private const val version = "3.1.0-beta"

        const val core = "com.google.android.libraries.maps:maps:$version"
        const val ktx = "com.google.maps.android:maps-v3-ktx:2.3.0"
        const val utility = "com.google.maps.android:android-maps-utils-v3:2.3.0"
        const val legacy = "com.google.android.gms:play-services-maps:17.0.1"
    }

    object Material {
        const val core = "com.google.android.material:material:_"
    }

    object AssistedInject {
        private const val version = "0.5.2"
        const val annotationDagger2 =
            "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processorDagger2 =
            "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }

    object Dagger {
        const val core = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Hilt {
        const val core = "com.google.dagger:hilt-android:${Versions.daggerhilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerhilt}"
        const val testing = "com.google.dagger:hilt-android-testing:${Versions.daggerhilt}"
        const val gradlePlugin =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerhilt}"
        const val gradlePluginSnapshot =
            "com.google.dagger:hilt-android-gradle-plugin:HEAD-SNAPSHOT"
    }

    object Firebase {
        private const val authVersion = "19.4.0"
        private const val bomVersion = "25.12.0"
        const val bom = "com.google.firebase:firebase-bom:$bomVersion"
        const val auth = "com.google.firebase:firebase-auth:$authVersion"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val crashlytics = "com.google.firebase:firebase-crashlytics"
        const val messaging = "com.google.firebase:firebase-messaging-ktx"
    }

    object GoogleAuth {
        const val auth = "com.google.android.gms:play-services-auth:${Versions.googleauth}"
    }

    object GoogleTruth {
        const val truth = "com.google.truth:truth:${Versions.googletruth}"
    }

    object JUnit {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object Kotlin {
        const val coroutinescore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
        const val coroutinesandroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:_"
        const val coroutinestest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:_"
        const val logging = "io.github.microutils:kotlin-logging:_"
    }

    object KotlinX {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:_"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:_"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:_"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:_"
        const val clientOkhttp = "io.ktor:ktor-client-okhttp:_"
        const val clientWebsockets = "io.ktor:ktor-client-websockets:_"
        const val clientContentNegotiation = "io.ktor:ktor-client-content-negotiation:_"
        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:_"
        const val json = "io.ktor:ktor-client-json:_"
        const val jsonAndroid = "io.ktor:ktor-client-json-jvm:_"
        const val android = "io.ktor:ktor-client-android:_"
        const val ios = "io.ktor:ktor-client-ios:_"
    }

    object Mockito {
        const val core = "org.mockito:mockito-core:${Versions.mockito}"
        const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    }

    object Moshi {
        const val core = "com.squareup.moshi:moshi:_"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:_"
        const val kotlincodegen = "com.squareup.moshi:moshi-kotlin-codegen:_"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:_"
        const val moshiconverter = "com.squareup.retrofit2:converter-moshi:_"
        const val scalarconverter = "com.squareup.retrofit2:converter-scalars:_"
    }

    object Robolectric {
        const val core = "org.robolectric:robolectric:${Versions.robolectric}"
    }

    object SqlDelight {
        const val runtime = "com.squareup.sqldelight:runtime:_"
        const val androidDriver = "com.squareup.sqldelight:android-driver:_"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:_"
        const val sqliteDriver = "com.squareup.sqldelight:sqlite-driver:_"
        const val coroutines =
            "com.squareup.sqldelight:coroutines-extensions-jvm:_"
        const val coroutinescore = "com.squareup.sqldelight:coroutines-extensions:_"
        const val coroutinesnative = "com.squareup.sqldelight:coroutines-extensions-native:_"
        const val paging =
            "com.squareup.sqldelight:android-paging-extensions:_"
        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:_"
    }

    object Timber {
        private const val version = "4.7.1"
        const val core = "com.jakewharton.timber:timber:_"
    }
}
