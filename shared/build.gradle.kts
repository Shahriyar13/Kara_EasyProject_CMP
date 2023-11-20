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
            export(app.duss.easyproject.Deps.ArkIvanov.Decompose.decompose)
            export(app.duss.easyproject.Deps.ArkIvanov.Essenty.lifecycle)
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
                with(app.duss.easyproject.Deps.Io.Ktor) {
                    api(ktorClientCore)
                    api(ktorSerializationKotlinxJson)
                    api(ktorClientContentNegotiation)
                    api(ktorClientLogging)
                }

                // Logback for ktor logging
                implementation(app.duss.easyproject.Deps.Logback.logbackClassic)

                // SqlDelight
                with(app.duss.easyproject.Deps.CashApp.SQLDelight) {
                    api(coroutinesExtensions)
                    api(primitiveAdapters)
                }

                // Koin
                with(app.duss.easyproject.Deps.Koin) {
                    api(core)
                    api(test)
                }

                // KotlinX Serialization Json
                implementation(app.duss.easyproject.Deps.Org.JetBrains.Kotlinx.kotlinxSerializationJson)

                // Coroutines
                implementation(app.duss.easyproject.Deps.Org.JetBrains.Kotlinx.coroutinesCore)

                // MVIKotlin
                with(app.duss.easyproject.Deps.ArkIvanov.MVIKotlin) {
                    api(mvikotlin)
                    api(mvikotlinMain)
                    api(mvikotlinExtensionsCoroutines)
                }

                // Decompose
                with(app.duss.easyproject.Deps.ArkIvanov.Decompose) {
                    api(decompose)
                    api(extensionsCompose)
                }

                // Image Loading
                api(app.duss.easyproject.Deps.Github.imageLoader)
                implementation(app.duss.easyproject.Deps.ArkIvanov.Essenty.lifecycle)
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
                implementation(app.duss.easyproject.Deps.Io.Ktor.ktorClientAndroid)

                // SqlDelight
                implementation(app.duss.easyproject.Deps.CashApp.SQLDelight.androidDriver)

                // Koin
                implementation(app.duss.easyproject.Deps.Koin.android)
            }
        }
        val androidUnitTest by getting

        val desktopMain by getting {
            dependsOn(commonMain)

            dependencies {
                // Ktor
                implementation(app.duss.easyproject.Deps.Io.Ktor.ktorClientJava)

                // SqlDelight
                implementation(app.duss.easyproject.Deps.CashApp.SQLDelight.sqliteDriver)
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
                implementation(app.duss.easyproject.Deps.Io.Ktor.ktorClientDarwin)

                // SqlDelight
                implementation(app.duss.easyproject.Deps.CashApp.SQLDelight.nativeDriver)

                // TouchLab
                implementation(app.duss.easyproject.Deps.Touchlab.statelyCommon)
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
    compileSdk = app.duss.easyproject.Configuration.compileSdk
    defaultConfig {
        minSdk = app.duss.easyproject.Configuration.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("PokemonDatabase") {
            packageName.set("app.duss.easyproject.core.database")
        }
    }
}