plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.quickconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quickconnect"
        minSdk = 23
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.databinding:databinding-runtime:8.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))





    // AndroidX Design Library
    implementation("com.google.android.material:material:1.11.0")

    // Lottie Animation Library
    implementation("com.airbnb.android:lottie:3.4.1")

    // Country Code Picker
    implementation("com.hbb20:ccp:2.5.2")

    // Circular Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Pin View
    implementation("io.github.chaosleung:pinview:1.4.4")

    // Image Cropper
    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // Animated Bottom Navigation Bar
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.3.4")

    // Firebase Database
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")


    // Volley
    implementation("com.android.volley:volley:1.2.1")

    // Image Picker
    //implementation("com.fxn769:pix:2.4.0")

    // Image Compressor
    //implementation("com.iceteck.silicompressor:silicompressor:2.2.3")
}