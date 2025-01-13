package uk.gov.onelogin.criorchestrator.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

private const val BASE_APPLICATION_ID = "uk.gov.onelogin.criorchestrator"
private const val BASE_NAMESPACE = "uk.gov.onelogin.criorchestrator"

/**
 * Type alias for configuring both Android application and Android library modules.
 */
private typealias AndroidExtension = CommonExtension<*, *, *, *, *, *>

/**
 * Set an application ID that starts with [BASE_APPLICATION_ID].
 */
fun ApplicationExtension.setApplicationId(suffix: String) {
    assert(suffix.isEmpty() || suffix.startsWith("."))
    defaultConfig {
       applicationId = "$BASE_APPLICATION_ID$suffix"
    }
}

/**
 * Set a namespace that starts with [BASE_NAMESPACE].
 */
fun AndroidExtension.setNamespace(suffix: String) {
    assert(suffix.isEmpty() || suffix.startsWith("."))
    namespace = "$BASE_NAMESPACE$suffix"
}

internal fun AndroidExtension.setJavaVersion() =
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(JAVA_VERSION)
        targetCompatibility = JavaVersion.toVersion(JAVA_VERSION)
    }

internal fun AndroidExtension.setPackagingConfig() =
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

internal fun AndroidExtension.setUiConfig() {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
}

internal fun ApplicationExtension.setBuildTypes() {
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

internal fun AndroidExtension.setInstrumentationTestingConfig() {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

