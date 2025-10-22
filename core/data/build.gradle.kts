import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.halimjr11.cameo.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 27

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val apiKey: String = localProperties.getProperty("API_KEY").orEmpty()
        val cert1: String = localProperties.getProperty("CERT_1").orEmpty()
        val cert2: String = localProperties.getProperty("CERT_2").orEmpty()
        val cert3: String = localProperties.getProperty("CERT_3").orEmpty()
        val baseUrl: String = localProperties.getProperty("BASE_URL").orEmpty()

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "CERT_1", "\"$cert1\"")
        buildConfigField("String", "CERT_2", "\"$cert2\"")
        buildConfigField("String", "CERT_3", "\"$cert3\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
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
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    api(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.okhttp)
    api(libs.logging.interceptor)
    api(libs.androidx.paging.runtime.ktx)
    api(libs.android.database.sqlcipher)
    api(libs.androidx.sqlite)
    debugApi(libs.library)
    releaseImplementation(libs.library.no.op)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}