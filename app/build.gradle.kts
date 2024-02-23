plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.nurhaqhalim.cameo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nurhaqhalim.cameo"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(project(":core"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    //coil
    implementation("io.coil-kt:coil:2.5.0")
    //shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    //viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")
    //window
    implementation("androidx.window:window:1.2.0")
    //test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}