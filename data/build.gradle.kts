plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.open.exchange.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

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
}

dependencies {
    implementation(Deps.coreKtx)
    //DataStore Preference
    implementation(Deps.data_store)
    // RETROFIT
    implementation(Deps.retrofit)
    implementation(Deps.gson)
    //ROOM DB
    implementation(Deps.room_runtime)
    implementation(Deps.room_ktx)
    kapt(Deps.room_compiler)
    annotationProcessor(Deps.room_compiler)
    //test relates
    testImplementation(Deps.junit)
    testImplementation(Deps.mockito)
    implementation(project(":domain"))
}