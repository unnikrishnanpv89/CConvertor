plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = App.APPLICATION_ID
    compileSdk = App.compileSdkVersion

    defaultConfig {
        applicationId = App.APPLICATION_ID
        minSdk = App.minSdkVersion
        targetSdk = App.targetSdkVersion
        versionCode = App.Version.CODE
        versionName = App.Version.NAME

        testInstrumentationRunner = "com.open.exchange.cconverter.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    android.buildFeatures.buildConfig=true

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableTransformForLocalTests = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //core
    implementation(Deps.coreKtx)
    implementation(Deps.activityKtx)
    // compose
    implementation(Deps.composeUI)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeUIPreview)
    implementation(Deps.composeMaterial3)
    implementation(Deps.activity)
    implementation(Deps.activityCompose)
    implementation(Deps.uiUtil)
    //systemui
    implementation(Deps.systemuicontroller)
    // hilt
    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltAndroidCompiler)
    kapt(Deps.hiltCompiler)
    // compose navigation
    implementation(Deps.composeNavigation)
    implementation(Deps.hiltNavigation)
    implementation(Deps.accompanistNav)
    implementation(Deps.accompanistPermission)
    //Retrofit
    implementation(Deps.retrofit)
    implementation(Deps.gson)
    implementation(Deps.loggingInterceptor)
    // splash screen
    implementation(Deps.splashScreen)
    //Room DB
    implementation(Deps.room_runtime)
    implementation(Deps.room_common)
    //DataStore Preference
    implementation(Deps.data_store)
    //testing related
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junit_ext)
    androidTestImplementation(Deps.espresso_core)
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:2.13.0")
    androidTestImplementation("org.mockito:mockito-android:2.24.5")
    androidTestImplementation(Deps.composeUITest)
    debugImplementation(Deps.composeUIManifest)
    testImplementation("org.mockito:mockito-inline:2.13.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    testImplementation("io.mockk:mockk-android:1.13.7")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.3")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.48")
    //other modules
    implementation(project(":data"))
    implementation(project(":domain"))
}