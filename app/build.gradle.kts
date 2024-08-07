plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ahargunyllib.athena"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ahargunyllib.athena"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Material3 Design
    // noinspection UseTomlInstead
    implementation("androidx.compose.material3:material3:1.2.1")

    // Architectural Components
    // noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    // noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    // Hilt
    // noinspection UseTomlInstead
    implementation("com.google.dagger:hilt-android:2.49")
    // noinspection UseTomlInstead
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    // noinspection UseTomlInstead
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    // noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    // noinspection UseTomlInstead
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //Room
    // noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:2.6.1")
    // noinspection KaptUsageInsteadOfKsp,UseTomlInstead
    kapt("androidx.room:room-compiler:2.6.1")
    // noinspection UseTomlInstead
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines
    // noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    // noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutines Lifecycle Scopes
    // noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    // noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
    // LiveData
    // noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")

    // Navigation Component
    // noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Coil
    // noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Glide
    // noinspection UseTomlInstead
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // noinspection UseTomlInstead
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // SplashScreen
    // noinspection UseTomlInstead
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Retrofit
    // noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // JSON Converter
    // noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Icon Extended
    // noinspection UseTomlInstead
    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")

    // Camera
    // noinspection UseTomlInstead
    implementation("androidx.camera:camera-core:1.3.4")
    // noinspection UseTomlInstead
    implementation("androidx.camera:camera-camera2:1.3.4")
    // noinspection UseTomlInstead
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    // noinspection UseTomlInstead
    implementation("androidx.camera:camera-view:1.3.4")

    // Google Maps Location Services
    // noinspection UseTomlInstead
    implementation("com.google.android.gms:play-services-location:21.3.0")
    // noinspection UseTomlInstead
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Google maps for compose
    // noinspection UseTomlInstead
    implementation("com.google.maps.android:maps-compose:5.0.3")

    // KTX for the Maps SDK for Android
    // noinspection UseTomlInstead
    implementation("com.google.maps.android:maps-ktx:5.0.0")

    // Google maps utils for compose
    // noinspection UseTomlInstead
    implementation("com.google.maps.android:maps-compose-utils:5.0.3")

    // KTX for the Maps SDK for Android Utility Library
    // noinspection UseTomlInstead
    implementation("com.google.maps.android:maps-utils-ktx:5.0.0")

    // Google map widgets for compose
    // noinspection UseTomlInstead
    implementation("com.google.maps.android:maps-compose-widgets:5.0.3")

    // Permissions
    // noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-permissions:0.20.0")

    // Socket
    // noinspection UseTomlInstead
    implementation("io.socket:socket.io-client:2.1.0")
}

kapt {
    correctErrorTypes = true
}