# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see

-keepclassmembers class com.happy.ricedetailsapp {
  public *;
}
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {

    static final long serialVersionUID;

    private static final java.io.ObjectStreamField[] serialPersistentFields;

    !static !transient <fields>;

    !private <fields>;

    !private <methods>;

    private void writeObject(java.io.ObjectOutputStream);

    private void readObject(java.io.ObjectInputStream);

    java.lang.Object writeReplace();

    java.lang.Object readResolve();

}

# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembernames class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

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
