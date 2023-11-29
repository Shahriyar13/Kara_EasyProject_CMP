import app.duss.easyproject.Configuration

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "app.duss.easyproject.android"
    compileSdk = app.duss.easyproject.Configuration.compileSdk
    defaultConfig {
        applicationId = "app.duss.easyproject.android"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = app.duss.easyproject.Versions.composeCompiler
    }
    packaging {
        resources {
            pickFirsts.add(
                "META-INF/INDEX.LIST"
            )
            excludes.addAll(
                listOf(
                    "META-INF/AL2.0",
                    "META-INF/LGPL2.1",
                ),
            )
        }
    }
    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
        }

        getByName("debug") {
            isMinifyEnabled = false
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
    implementation(project(":shared"))
    implementation(app.duss.easyproject.Deps.Androidx.Activity.activityCompose)

    // Koin
    with(app.duss.easyproject.Deps.Koin) {
        api(android)
    }
}