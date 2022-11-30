buildscript {
    extra.apply {
        set("compose_version", "1.1.1")
        set("compile_sdk", 32)
        set("target_sdk", 32)
        set("min_sdk", 23)
    }
    val compose_version by extra("1.1.1")
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.3.10" apply false
}