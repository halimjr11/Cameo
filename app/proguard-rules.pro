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

# Keep Koin core classes
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Keep your modules (Koin uses reflection to load them)
-keep class com.halimjr11.cameo.di.** { *; }

# Keep ViewModel and its constructors for Koin
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}
-keep class com.halimjr11.cameo.view.feature.**.viewmodel.** { *; }

# Optional: keep singleton/inject classes
-keep class com.halimjr11.cameo.view.feature.**.di.** { *; }
-keepnames class com.halimjr11.cameo.core.**.di.** { *; }

# Keep ACTIVITY & FRAGMENT
-keep class com.halimjr11.cameo.view.** { *; }
-keepclassmembers class * extends androidx.fragment.app.Fragment {
    public <init>();
}
-keepclassmembers class * extends android.app.Activity {
    public <init>();
}

# Keep all generated ViewBinding classes
-keep class **Binding { *; }

# Keep all generated DataBinding classes
-keep class **BindingImpl { *; }

# Keep ANDROID / KOTLIN ANNOTATIONS
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.inject.**
-dontwarn kotlin.Unit
-dontwarn kotlin.Metadata

# Keep all GENERAL RULES FOR APP
-keepattributes Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-dontwarn com.google.errorprone.annotations.**
-dontwarn androidx.lifecycle.**
