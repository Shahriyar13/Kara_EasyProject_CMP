import app.duss.easyproject.Configuration
import app.duss.easyproject.Deps
import app.duss.easyproject.Versions

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "app.duss.easyproject.android"
    compileSdk = app.duss.easyproject.Configuration.compileSdk
    defaultConfig {
        applicationId = "app.duss.easyproject.android"
        minSdk = app.duss.easyproject.Configuration.minSdk
        targetSdk = app.duss.easyproject.Configuration.targetSdk
        versionCode = app.duss.easyproject.Configuration.versionCode
        versionName = app.duss.easyproject.Configuration.versionName
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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