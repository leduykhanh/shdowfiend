#Use 5 step of optimization
#-optimizationpasses 5

#When not preverifing in a case-insensitive filing system, such as Windows. This tool will unpack your processed jars,(if using windows you should then use):
#-dontusemixedcaseclassnames

#Specifies not to ignore non-public library classes. As of version 4.5, this is the default setting
#-dontskipnonpubliclibraryclasses

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify

-dontwarn com.badlogic.gdx.**

#Specifies to write out some more information during processing. If the program terminates with an exception, this option will print out the entire stack trace, instead of just the exception message.
-verbose
-keepnames class com.badlogic.gdx.backends.android.AndroidInput*
-keepclassmembers class com.badlogic.gdx.backends.android.AndroidInput* {<init>(...);}

#-keep class com.badlogic.gdx.**
#-keep class com.badlogic.gdx.scenes.** { *; }
-keep class jangkoo.shadowfiend.** {*;}
-keep class com.badlogic.** {*;}
-keep class com.badlogic.gdx.backends.** {*;}
-keep class com.badlogic.gdx.backends.lwjgl.** {*;}
-keep class com.badlogic.gdx.backends.android.** {*;}
-keep class com.google.** {*;}