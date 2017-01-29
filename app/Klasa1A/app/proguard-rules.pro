# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\bartl\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn com.google.common.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.**
-dontwarn java.io.IOException
-dontwarn com.squareup.picasso.**
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers class pl.rasztabiga.klasa1a.models.** { *; }
-keepclassmembers class pl.rasztabiga.klasa1a.data.LuckyNumbers { *; }
-keepclassmembers class pl.rasztabiga.klasa1a.data.OnDuties { *; }
-keepclassmembers class pl.rasztabiga.klasa1a.data.Student { *; }