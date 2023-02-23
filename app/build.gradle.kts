import org.jetbrains.kotlin.serialization.builtins.main

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}
apply(from = "buildTypes.gradle")

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = VersionApp.targetSdkVersion

    defaultConfig {
        applicationId = "com.amarturelo.usersgithub.followers.demo"
        minSdk = VersionApp.minSdkVersion
        targetSdk = VersionApp.targetSdkVersion
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        renderscriptTargetApi = 21
        renderscriptSupportModeEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes.add("META-INF/proguard/androidx-annotations.pro")
        resources.excludes.add("META-INF/androidx.exifinterface_exifinterface.version")
        resources.pickFirsts.add("lib/armeabi-v7a/libtool-checker.so")
        resources.pickFirsts.add("lib/arm64-v8a/libtool-checker.so")
        resources.pickFirsts.add("lib/x86/libtool-checker.so")
        resources.pickFirsts.add("lib/x86_64/libtool-checker.so")
        resources.excludes.add("META-INF/*.kotlin_module")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    kapt(ApplicationDependencies.daggerCompiler)
    kapt(ApplicationDependencies.daggerAndroidProcessor)

    // Coroutines
    implementation(ApplicationDependencies.coroutinesCore)
    implementation(ApplicationDependencies.coroutinesAndroid)

    // hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltAndroidCompiler)

    //Logger
    implementation(ApplicationDependencies.timber)

    //okHttp
    implementation(ApplicationDependencies.okhttp3LoggingInterceptor)
    implementation(ApplicationDependencies.okhttp3)
    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.retrofitGson)

    //Navigation
    implementation(ApplicationDependencies.xNavComponentFragmentKtx)
    implementation(ApplicationDependencies.xNavComponentUiKtx)

    implementation(project(path = ":followers")) {
        //exclude(group = "androidx.core")
    }

    //modules
    implementation(ApplicationModules.ugCore)

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            freeCompilerArgs.plus("-Xjvm-default=all-compatibility")
        }
    }
}