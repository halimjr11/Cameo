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

-ignorewarnings
-dontoptimize
-dontshrink
-allowaccessmodification
-overloadaggressively
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, AnnotationDefault

-keep class * extends android.app.Application {
    void attachBaseContext(android.content.Context);
    void onCreate();
}
-keep class * extends android.app.Activity
-keep class * extends androidx.fragment.app.Fragment {
    public <init>();
}
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider

# Core Koin components
-keep class org.koin.core.component.KoinComponent
-keep class org.koin.core.annotation.** { *; }
-keep class org.koin.core.component.** { *; }
-dontwarn org.koin.**

-keep class com.halimjr11.cameo.view.feature.*.viewmodel.*ViewModel
-keep class com.halimjr11.cameo.di.*Module { *; }

-keep class **Binding { *; }
-keep class **BindingImpl { *; }

-dontwarn org.jetbrains.annotations.**
-dontwarn javax.inject.**
-dontwarn kotlin.Unit
-dontwarn kotlin.Metadata
-dontwarn androidx.lifecycle.viewmodel.CreationExtras
-dontwarn androidx.lifecycle.viewmodel.CreationExtras$Key
-dontwarn com.google.errorprone.annotations.**

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

