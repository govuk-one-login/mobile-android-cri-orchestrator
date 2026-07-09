package uk.gov.onelogin.criorchestrator.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

private const val BASE_APPLICATION_ID = "uk.gov.onelogin.criorchestrator"
internal const val BASE_NAMESPACE = "uk.gov.onelogin.criorchestrator"

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
fun CommonExtension.setNamespace(project: Project) {
    val suffix = project.modulePathAsPackage()
    val namespace = "$BASE_NAMESPACE.$suffix"
    this.namespace = namespace
}

internal fun CommonExtension.setJavaVersion() {
    compileOptions.apply {
        sourceCompatibility = JavaVersion.toVersion(JAVA_VERSION)
        targetCompatibility = JavaVersion.toVersion(JAVA_VERSION)
    }
}

internal fun CommonExtension.setPackagingConfig() {
    packaging.apply {
        resources {
            merges += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "/META-INF/{LICENSE.md,LICENSE-notice.md}"
            merges += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
}

internal fun CommonExtension.setUiConfig() {
    defaultConfig.apply {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures.apply {
        compose = true
    }
}

internal fun ApplicationExtension.setBuildTypes() {
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

internal fun CommonExtension.setInstrumentationTestingConfig() {
    defaultConfig.apply {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

