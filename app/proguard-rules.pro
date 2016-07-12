# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\work\android-sdk\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# 过滤R文件的混淆：
-keep class **.R$* {*;}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

 # 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-dontwarn org.apache.http.**
-keep class org.apache.http.** { *; }

-dontwarn android.support.v4.**
-dontwarn android.annotation
-dontwarn com.squareup.**
-keepattributes *Annotation*

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn okio.**

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#所有native的方法不能去混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# aidl文件不能去混淆.
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#不优化泛型和反射
-keepattributes Signature

#fastjson
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-keep class com.alibaba.fastjson.** { *; }
-keepclassmembers class * {
public <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}

-keep public class * implements java.io.Serializable {
    public *;
}


#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepnames class * { @butterknife.Bind *;}

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


# #  ######## greenDao混淆  ##########
-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keep class android.support.v4.widget.DrawerLayout { *; }
-keep class android.support.test.espresso.IdlingResource { *; }
-keep class com.google.common.base.Preconditions { *; }

#For Guava:
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

# Proguard rules that are applied to your test apk/code.
-ignorewarnings

-keepattributes *Annotation*

-dontnote junit.framework.**
-dontnote junit.runner.**

-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**
-dontwarn com.squareup.javawriter.JavaWriter
# Uncomment this if you use Mockito
-dontwarn org.mockito.**