plugins {
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvm("desktop") {
        jvmToolchain(11)
    }

    androidTarget{
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "KCommerce the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
            export(libs.arkivanov.decompose)
            export(libs.arkivanov.essenty.lifecycle)
//            export(libs.calf.ui)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Ktor
                api(libs.ktor.client.core)
                api(libs.ktor.client.resources)
                api(libs.ktor.serialization.kotlinx.json)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.client.logging)
                api(libs.ktor.client.auth)

                // Logback for ktor logging
                implementation(libs.logback.logback.classic)

                // SqlDelight
                api(libs.sqldelight.extensions.coroutines)
                api(libs.sqldelight.adapters.primitive)

                // Koin
                api(libs.koin.core)
                api(libs.koin.test)

                // KotlinX Serialization Json
                implementation(libs.kotlinx.serialization.json)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)


                // MVIKotlin
                api(libs.arkivanov.mvi)
                api(libs.arkivanov.mvi.main)
                api(libs.arkivanov.mvi.coroutines)

                // Decompose
                api(libs.arkivanov.decompose)
                api(libs.arkivanov.decompose.compose)
                implementation(libs.arkivanov.essenty.lifecycle)

                // Image Loading
                api(libs.image.loader)

                // Calf
//                api(libs.calf.ui)
                implementation(libs.calf.file.picker)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor
                implementation(libs.ktor.client.android)

                // SqlDelight
                implementation(libs.sqldelight.driver.android)

                // Koin
                implementation(libs.koin.android)

            }
        }
        val androidUnitTest by getting

        val desktopMain by getting {
            dependencies {
                // Ktor
                implementation(libs.ktor.client.java)

                // SqlDelight
                implementation(libs.sqldelight.driver.sqlite)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // Ktor
                implementation(libs.ktor.client.darwin)

                // SqlDelight
                implementation(libs.sqldelight.driver.native)

                // TouchLab
                implementation(libs.touchlab.stately.common)
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

android {
    namespace = "app.duss.easyproject"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("app.duss.easyproject.core.database")
        }
    }
}