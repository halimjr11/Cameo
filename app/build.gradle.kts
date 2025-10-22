import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.navigation.safe.args)
}

android {
    namespace = "com.halimjr11.cameo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.halimjr11.cameo"
        minSdk = 27
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    dynamicFeatures += setOf(":favorite")
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:components"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:resources"))
    debugImplementation(libs.leakcanary.android)
}