plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    `maven-publish`
}

group = ConfigData.groupName
version = ConfigData.versionName

apply(from = "jacoco.gradle")

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

    // hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltAndroidCompiler)
    kapt(ApplicationDependencies.xHiltCompiler)

    // test
    testImplementation(UnitTestingDependencies.junit)
    testImplementation(UnitTestingDependencies.mockito)
    testImplementation(UnitTestingDependencies.mockWebServer)
    testImplementation(UnitTestingDependencies.xLifecycleViewmodel)
    testImplementation(UnitTestingDependencies.coroutinesTestKt)
    testImplementation(UnitTestingDependencies.xCoreTesting)
    testImplementation(UnitTestingDependencies.mockito)
    androidTestImplementation(UnitTestingDependencies.testRunner)
    testImplementation(UnitTestingDependencies.mockk)
    implementation(UnitTestingDependencies.junit)

    implementation(ApplicationDependencies.retrofit)
    implementation(ApplicationDependencies.retrofitGson)
    implementation(ApplicationDependencies.okhttp)
    implementation(ApplicationDependencies.okhttpLoggingInterceptor)

    implementation(ApplicationDependencies.kotlinStdLib)

    implementation(ApplicationDependencies.xLifecycleViewmodel)
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

    implementation("com.amarturelo.usersgithub:core:1.0.0")

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

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/amarturelo/usersgithub-android-followers")
            credentials {
                username = (System.getenv("GITHUB_USER") ?: project.properties["GITHUB_USER"]).toString()
                password = (System.getenv("GITHUB_ACCESS_TOKEN")
                    ?: project.properties["GITHUB_ACCESS_TOKEN"]).toString()
            }
        }
    }
    publications {
        register("followers", MavenPublication::class) {
            groupId = ConfigData.groupName
            artifactId = ConfigData.artifactId
            version = ConfigData.versionName
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }
}
