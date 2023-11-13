import app.duss.easyproject.Configuration
import app.duss.easyproject.Deps

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    id("app.cash.sqldelight")
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    jvm("desktop") {
//        jvmToolchain(17)
    }

    androidTarget{
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
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
            export(Deps.ArkIvanov.Decompose.decompose)
            export(Deps.ArkIvanov.Essenty.lifecycle)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose
                with(compose) {
                    api(runtime)
                    api(foundation)
                    api(material)
                    api(material3)
                    api(materialIconsExtended)
                }

                // Ktor
                with(Deps.Io.Ktor) {
                    api(ktorClientCore)
                    api(ktorClientResources)
                    api(ktorSerializationKotlinxJson)
                    api(ktorClientContentNegotiation)
                    api(ktorClientLogging)
                    api(ktorClientAuth)
                }

                // Logback for ktor logging
                implementation(Deps.Logback.logbackClassic)

                // SqlDelight
                with(Deps.CashApp.SQLDelight) {
                    api(coroutinesExtensions)
                    api(primitiveAdapters)
                }

                // Koin
                with(Deps.Koin) {
                    api(core)
                    api(test)
                }

                with(Deps.Org.JetBrains.Kotlinx) {
                    // KotlinX Serialization Json
                    implementation(kotlinxSerializationJson)
                    // Coroutines
                    implementation(coroutinesCore)
                }

                // MVIKotlin
                with(Deps.ArkIvanov.MVIKotlin) {
                    api(mvikotlin)
                    api(mvikotlinMain)
                    api(mvikotlinExtensionsCoroutines)
                }

                // Decompose
                with(Deps.ArkIvanov.Decompose) {
                    api(decompose)
                    api(extensionsCompose)
                }

                // Image Loading
                api(Deps.Github.imageLoader)
                implementation(Deps.ArkIvanov.Essenty.lifecycle)

                // File Picker
                implementation(Deps.Github.filePicker)
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
                implementation(Deps.Io.Ktor.ktorClientAndroid)

                // SqlDelight
                implementation(Deps.CashApp.SQLDelight.androidDriver)

                // Koin
                implementation(Deps.Koin.android)
            }
        }
        val androidUnitTest by getting

        val desktopMain by getting {
            dependsOn(commonMain)

            dependencies {
                // Ktor
                implementation(Deps.Io.Ktor.ktorClientJava)

                // SqlDelight
                implementation(Deps.CashApp.SQLDelight.sqliteDriver)
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
                implementation(Deps.Io.Ktor.ktorClientDarwin)

                // SqlDelight
                implementation(Deps.CashApp.SQLDelight.nativeDriver)

                // TouchLab
                implementation(Deps.Touchlab.statelyCommon)
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
        kotlinOptions.jvmTarget = "17"
    }
}

android {
    namespace = "app.duss.easyproject"
    compileSdk = Configuration.compileSdk
    defaultConfig {
        minSdk = Configuration.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("app.duss.easyproject.core.database")
        }
    }
}