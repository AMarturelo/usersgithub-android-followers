import org.jetbrains.kotlin.serialization.builtins.main

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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

    dataBinding {
        this.isEnabled = true
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Coroutines
    implementation(ApplicationDependencies.coroutinesCore)
    implementation(ApplicationDependencies.coroutinesAndroid)

    //dagger
    implementation(ApplicationDependencies.dagger)
    implementation(ApplicationDependencies.daggerAndroid)
    implementation(ApplicationDependencies.daggerSupport)
    kapt(ApplicationDependencies.daggerCompiler)
    kapt(ApplicationDependencies.daggerAdroidProcessor)

    //Logger
    implementation(ApplicationDependencies.timber)

    //okHttp
    implementation(ApplicationDependencies.okhttp3LoggingInterceptor)
    implementation(ApplicationDependencies.okhttp3)
    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.gson)

    implementation(project(path = ":followers")) {
        //exclude(group = "androidx.core")
    }

    //modules
    implementation(MainApplicationDependencies.ugCore)

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            freeCompilerArgs.plus("-Xjvm-default=all-compatibility")
        }
    }
}