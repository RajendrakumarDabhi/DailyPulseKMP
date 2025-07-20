import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization) // <--- APPLY THE PLUGIN HERE
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    //noinspection WrongGradleMethod
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here

            //Rest API
            implementation(libs.ktor.client.core) // Or the latest version
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            // For logging (optional but recommended)
            implementation(libs.ktor.client.logging)

            //DI Koin
            implementation(libs.koin.core) // Core Koin library
            implementation(libs.koin.ktor)  // For Ktor integration (optional, but can be useful)

            implementation(libs.kotlinx.datetime)

        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.androidx.lifecycle.viewmodel.android)

            //DI
            implementation(libs.koin.android) // For Android-specific Koin features (like viewModelScope)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) // For modern KMM projects
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.rajendra.dailypulsekmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
