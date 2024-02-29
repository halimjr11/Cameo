plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nurhaqhalim.cameo.core"
    compileSdk = 34
    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField(
            "String",
            "API_KEY",
            "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MmQ2M2ZmN2E4NzBhZmY0YWE5YTA0NTA4NGM1ODI0NyIsInN1YiI6IjYwN2FmZWIwMWIxZjNjMDA2ZWFmN2Q0NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4BLTz2rIkUONAMgdfrbiCANIbSRbKtqjTBXeb2cuwuM\""
        )

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to projectDir.resolve("schemas").toString(),
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
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
}

dependencies {
    //api retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    //data store
    api("androidx.datastore:datastore-preferences:1.0.0")
    //room
    api("androidx.room:room-ktx:2.6.1")
    api("androidx.room:room-runtime:2.6.1")
    api("androidx.room:room-paging:2.6.1")
    api("androidx.sqlite:sqlite-ktx:2.4.0")
    api("net.zetetic:android-database-sqlcipher:4.5.3")
    //paging3
    api("androidx.paging:paging-runtime-ktx:3.2.1")
    //navigation
    api("androidx.navigation:navigation-fragment-ktx:2.7.7")
    api("androidx.navigation:navigation-ui-ktx:2.7.7")
    //lifecycle
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    api("androidx.lifecycle:lifecycle-extensions:2.2.0")
    api("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //koin
    api("io.insert-koin:koin-core:3.5.3")
    api("io.insert-koin:koin-android:3.5.3")
    //coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //firebase
    api(platform("com.google.firebase:firebase-bom:32.7.2"))
    api("com.google.firebase:firebase-auth-ktx")
    api("com.google.firebase:firebase-analytics-ktx")
    api("com.google.firebase:firebase-crashlytics-ktx")
    api("com.google.firebase:firebase-config-ktx")
    api("com.google.firebase:firebase-messaging-ktx")
    api("com.google.firebase:firebase-storage-ktx")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}