package app.duss.easyproject

object Deps {
    object Org {
        object JetBrains {
            object Kotlinx {
                const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${app.duss.easyproject.Versions.coroutines}"

                const val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${app.duss.easyproject.Versions.serialization}"
            }

            object KotlinWrappers {
                const val kotlinWrappersBom = "org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:${app.duss.easyproject.Versions.kotlinWrappersBom}"
                const val kotlinStyled = "org.jetbrains.kotlin-wrappers:kotlin-styled"
            }
        }
    }

    object Androidx {
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:${app.duss.easyproject.Versions.activityCompose}"
        }
    }

    object Io {
        object Ktor {
            const val ktorClientCore = "io.ktor:ktor-client-core:${app.duss.easyproject.Versions.ktor}"
            const val ktorSerializationKotlinxJson = "io.ktor:ktor-serialization-kotlinx-json:${app.duss.easyproject.Versions.ktor}"
            const val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:${app.duss.easyproject.Versions.ktor}"
            const val ktorClientLogging = "io.ktor:ktor-client-logging:${app.duss.easyproject.Versions.ktor}"

            // Engines
            const val ktorClientAndroid = "io.ktor:ktor-client-android:${app.duss.easyproject.Versions.ktor}"
            const val ktorClientDarwin = "io.ktor:ktor-client-darwin:${app.duss.easyproject.Versions.ktor}"
            const val ktorClientJava = "io.ktor:ktor-client-java:${app.duss.easyproject.Versions.ktor}"
            const val ktorClientJs = "io.ktor:ktor-client-js:${app.duss.easyproject.Versions.ktor}"
        }
    }

    object Logback {
        const val logbackClassic = "ch.qos.logback:logback-classic:${app.duss.easyproject.Versions.logbackClassic}"
    }

    object CashApp {
        object SQLDelight {
            const val gradlePlugin = "app.cash.sqldelight:gradle-plugin:${app.duss.easyproject.Versions.sqlDelight}"
            const val androidDriver = "app.cash.sqldelight:android-driver:${app.duss.easyproject.Versions.sqlDelight}"
            const val sqliteDriver = "app.cash.sqldelight:sqlite-driver:${app.duss.easyproject.Versions.sqlDelight}"
            const val nativeDriver = "app.cash.sqldelight:native-driver:${app.duss.easyproject.Versions.sqlDelight}"
            const val sqljsDriver = "app.cash.sqldelight:sqljs-driver:${app.duss.easyproject.Versions.sqlDelight}"

            const val coroutinesExtensions = "app.cash.sqldelight:coroutines-extensions:${app.duss.easyproject.Versions.sqlDelight}"
            const val primitiveAdapters = "app.cash.sqldelight:primitive-adapters:${app.duss.easyproject.Versions.sqlDelight}"
        }
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${app.duss.easyproject.Versions.koin}"
        const val test = "io.insert-koin:koin-test:${app.duss.easyproject.Versions.koin}"
        const val android = "io.insert-koin:koin-android:${app.duss.easyproject.Versions.koin}"
    }

    object ArkIvanov {
        object MVIKotlin {
            const val mvikotlin = "com.arkivanov.mvikotlin:mvikotlin:${app.duss.easyproject.Versions.mviKotlin}"
            const val mvikotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:${app.duss.easyproject.Versions.mviKotlin}"
            const val mvikotlinExtensionsCoroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${app.duss.easyproject.Versions.mviKotlin}"
        }

        object Decompose {
            const val decompose = "com.arkivanov.decompose:decompose:${app.duss.easyproject.Versions.decompose}"
            const val extensionsCompose = "com.arkivanov.decompose:extensions-compose-jetbrains:${app.duss.easyproject.Versions.decompose}"
        }

        object Essenty {
            const val lifecycle = "com.arkivanov.essenty:lifecycle:${app.duss.easyproject.Versions.essenty}"
        }
    }

    object Github {
        const val imageLoader = "io.github.qdsfdhvh:image-loader:${app.duss.easyproject.Versions.imageLoader}"
    }

    object Touchlab {
        const val statelyCommon = "co.touchlab:stately-common:${app.duss.easyproject.Versions.touchlab}"
    }
}