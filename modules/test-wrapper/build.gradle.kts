import com.android.build.api.dsl.ApplicationExtension
import uk.gov.onelogin.criorchestrator.extensions.setApplicationId
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
import uk.gov.pipelines.extensions.ProjectExtensions.versionCode
import uk.gov.pipelines.extensions.ProjectExtensions.versionName

plugins {
    id("uk.gov.onelogin.criorchestrator.android-app-config")
}

android {
    buildFeatures {
        buildConfig = true
        compose = true
    }

    dataBinding { enable = true }
    productFlavors {
        flavorDimensions.add("env")

        listOf(
            "build",
            "staging",
        ).forEach { flavourString ->
            create(flavourString) {
                dimension = "env"
                applicationIdSuffix = ".$flavourString"
            }
        }
    }
}

configure<ApplicationExtension> {
    setApplicationId(suffix = ".testwrapper")
    setNamespace(suffix = ".testwrapper")

    defaultConfig {
        versionCode = project.versionCode
        versionName = project.versionName
    }
}
