plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = VersionApp.compileSdkVersion

    defaultConfig {
        minSdk = VersionApp.minSdkVersion
        targetSdk = VersionApp.targetSdkVersion
        renderscriptSupportModeEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        unitTests.all {
            it.jvmArgs(
                "-Xms2g",
                "-Xmx2g",
                "-XX:+DisableExplicitGC"
            )
            it.testLogging {
                events("passed", "skipped", "failed", "standardOut", "standardError")
            }
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    lint {
        disable("ObsoleteLintCustomCheck", "TypographyFractions", "TypographyQuotes")
        isAbortOnError = false
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

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation(ApplicationDependencies.lifecycleViewmodel)
    implementation(ApplicationDependencies.lifecycleRuntime)
    implementation(ApplicationDependencies.lifecycleLivedata)

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
    implementation(ApplicationDependencies.okhttp3)
    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.gson)
    implementation(ApplicationDependencies.okhttp3)

    //epoxy
    implementation("com.airbnb.android:epoxy:4.1.0")
    kapt("com.airbnb.android:epoxy-processor:4.1.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

    implementation(MainApplicationDependencies.ugCore)

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            freeCompilerArgs.plus("-Xjvm-default=all-compatibility")
        }
    }
}

android.libraryVariants.forEach { variant ->
    if (variant.name.contains("debug")) {
        variant.unitTestVariant?.let {
            val aptOutputDir = File(buildDir, "generated/source/apt/${it.dirName}")
            it.addJavaSourceFoldersToModel(aptOutputDir)
        }
    }
}

val androidSourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

artifacts {
    this.archives(androidSourcesJar)
}