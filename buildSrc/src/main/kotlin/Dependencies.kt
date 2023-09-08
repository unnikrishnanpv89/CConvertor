object classPath{
    val hiltClass by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}" }
    val kotlinClass by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"}
}

/**
 * To define dependencies
 */
object Deps {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompatVersion}" }

    //compose
    val composeUI by lazy { "androidx.compose.ui:ui:${Versions.composeVersion}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.composeVersion}" }
    val composeUIPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.composeMaterial3}" }
    val activity by lazy { "androidx.activity:activity:${Versions.activityCompose}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeUITest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}" }
    val composeUITooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.composeVersion}" }
    val composeUIManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}" }
    val uiUtil by lazy { "androidx.compose.ui:ui-util:${Versions.uiUtilVersion}" }

    //compose navigation
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigation}" }
    val hiltNavigation by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}" }
    val accompanistPermission by lazy {"com.google.accompanist:accompanist-permissions:${Versions.accompanistNavigation}"}
    val accompanistNav by lazy {"com.google.accompanist:accompanist-navigation-animation:${Versions.accompanistNavigation}"}

    //timber
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timberVersion}" }

    //hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltVersion}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}" }


    // RETROFIT
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val gson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}" }
    val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}" }

    //core
    val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}" }

    //Rakuten user sdk
    val userSdk by lazy { "jp.co.rakuten.sdtd:user:${Versions.userSdkVersion}" }

    //image loading
    val glide by lazy { "com.github.skydoves:landscapist-glide:${Versions.glideVersion}" }

    //coil
    val coil by lazy {"io.coil-kt:coil-compose:${Versions.coilVersion}" }
    val coilTransformation by lazy { "com.github.Commit451.coil-transformations:transformations:" +
            Versions.coilTransformation
    }

    //exoplayer
    val exoplayer by lazy {"com.google.android.exoplayer:exoplayer-core:${Versions.exoplayerVersion}" }
    val exoplayer_ui by lazy {"com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayerVersion}"}
    val exoplayer_dash by lazy {"com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayerVersion}"}
    val exoplayer_mediaSession by lazy {"com.google.android.exoplayer:extension-mediasession:${Versions.exoplayerVersion}"}
    val exoplayer_hls by lazy {"com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayerVersion}"}

    val legacySupport by lazy {"androidx.legacy:legacy-support-v4:${Versions.legacySupport}"}
    val volley by lazy {"com.android.volley:volley:${Versions.volleyVersion}"}

    // splash screen
    val splashScreen by lazy {"androidx.core:core-splashscreen:${Versions.splashScreen}"}

    //Material design
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }

    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}" }

    //unit test & instrumentation test
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val junit_ext by lazy { "androidx.test.ext:junit:${Versions.junit_ext_version}" }
    val espresso_core by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso_version}" }
    val mockito by lazy {"org.mockito:mockito-core:${Versions.mockito_version}"}
    //systemuicontroller
    val systemuicontroller by lazy { "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemuiController}" }

    //room database
    val room_compiler by lazy {"androidx.room:room-compiler:${Versions.roomVersion}" }
    val room_runtime by lazy {"androidx.room:room-runtime:${Versions.roomVersion}" }
    val room_ktx by lazy {"androidx.room:room-ktx:${Versions.roomVersion}" }
    val room_common by lazy {"androidx.room:room-common:${Versions.roomVersion}" }

    //Preference
    val data_store by lazy {"androidx.datastore:datastore-preferences:${Versions.dataStore_Version}" }
}