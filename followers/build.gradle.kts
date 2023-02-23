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

    /* Google-Android libraries */
    implementation(ApplicationDependencies.xAppCompat)
    implementation(ApplicationDependencies.xDesign)
    implementation(ApplicationDependencies.xConstraintLayout)
    implementation(ApplicationDependencies.kotlinStdLib)
    implementation(ApplicationDependencies.xNavComponentFragmentKtx)
    implementation(ApplicationDependencies.xNavComponentUiKtx)

    // dagger
    implementation(ApplicationDependencies.dagger)
    implementation(ApplicationDependencies.daggerAndroid)
    implementation(ApplicationDependencies.daggerAndroidSupport)
    kapt(ApplicationDependencies.daggerCompiler)
    kapt(ApplicationDependencies.daggerAndroidProcessor)

    // test
    testImplementation(UnitTestingDependencies.junit)
    testImplementation(UnitTestingDependencies.mockito)
    testImplementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    testImplementation(UnitTestingDependencies.xCoreTesting)
    testImplementation(UnitTestingDependencies.mockito)
    androidTestImplementation("androidx.test:runner:1.4.0")
    testImplementation("io.mockk:mockk:1.12.2")
    implementation(UnitTestingDependencies.junit)

    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.retrofitGson)
    implementation(ApplicationDependencies.okhttp)
    implementation(ApplicationDependencies.okhttpLoggingInterceptor)

    implementation(ApplicationDependencies.kotlinStdLib)

    implementation(ApplicationDependencies.lifecycleViewmodel)
    implementation(ApplicationDependencies.lifecycleRuntime)
    implementation(ApplicationDependencies.lifecycleLivedata)

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Coroutines
    implementation(ApplicationDependencies.coroutinesCore)
    implementation(ApplicationDependencies.coroutinesAndroid)

    //Logger
    implementation(ApplicationDependencies.timber)

    implementation(ApplicationDependencies.epoxy)
    kapt(ApplicationDependencies.epoxyProcessor)

    //Glide
    implementation(ApplicationDependencies.glide)
    kapt(ApplicationDependencies.glideCompiler)

    implementation(ApplicationModules.ugCore)

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