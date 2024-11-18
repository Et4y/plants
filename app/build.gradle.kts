plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.softxpert.plants"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.softxpert.plants"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    ksp {
        arg("dagger.hilt.internal.useAggregatingRootProcessor", "true") // Required for Hilt + KSP
        arg("dagger.fastInit", "enabled") // Optional for faster initialization
        arg("dagger.hilt.android.internal.projectType", "APP") // Set to LIBRARY if it's a library module
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    dataBinding {
        enable = true
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

    implementation(libs.glide)
    ksp(libs.glide.compiler)
    implementation(libs.constraintlayout)

//    navigation
    implementation(libs.navigation)
    implementation(libs.navigation.ui)

//    room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

//    retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.coroutine.adapter)
    implementation(libs.coroutine.experimental.adapter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logging.interceptor) {
        exclude(group = "org.json", module = "json")
    }

//    hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.fragment)
}