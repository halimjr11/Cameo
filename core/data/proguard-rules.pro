# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## --- SQLCipher ---
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

## --- Gson ---
-keepattributes Signature, *Annotation*
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-dontwarn sun.misc.**

## --- Retrofit ---
-keepattributes Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# --- Retrofit / OkHttp ---
-dontwarn okhttp3.**
# Keep core public API
-keep class okhttp3.OkHttpClient { *; }
-keep class okhttp3.Request { *; }
-keep class okhttp3.Response { *; }
-keep class okhttp3.Interceptor { *; }
-keep class okhttp3.logging.HttpLoggingInterceptor { *; }

# SSL Pinning (certificate pinner)
-keep class okhttp3.CertificatePinner { *; }
-dontwarn javax.annotation.**
-keepattributes Signature
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn kotlinx.**
-dontwarn kotlin.Unit

## --- Room ---
-keep class androidx.room.paging.** { *; }
-keep class androidx.sqlite.db.** { *; }
-keep class * extends androidx.room.RoomDatabase
-keepclassmembers class * {
    @androidx.room.* <methods>;
    @androidx.room.* <fields>;
}

## --- Model Entity ---
-keep class com.halimjr11.cameo.data.local.model.** { *; }
-keepnames class com.halimjr11.cameo.data.remote.model.** { *; }
