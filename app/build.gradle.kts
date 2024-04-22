plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // SafeArgs
    id("androidx.navigation.safeargs.kotlin")
    // Firebase
    id("com.google.gms.google-services")
    // Parcelize
    id("kotlin-parcelize")
    // Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.zaaydar.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zaaydar.movieapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Navigaton
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore")

    // Retrofit
    val ret_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$ret_version")
    annotationProcessor("com.squareup.retrofit2:retrofit:$ret_version")
    implementation("com.squareup.retrofit2:converter-gson:$ret_version")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$ret_version")


    //RxJava
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    //Glide
    val gli_ver = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$gli_ver")

    //Hilt
    val hilt_ver = "2.48"
    implementation("com.google.dagger:hilt-android:$hilt_ver")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_ver")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    // Chromecast Sender
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}