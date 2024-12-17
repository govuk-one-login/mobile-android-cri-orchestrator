import com.android.build.api.dsl.ApplicationExtension
import uk.gov.onelogin.criorchestrator.extensions.setApplicationId
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
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
    val semanticVersion = project.versionName
    val (first, second, third) = semanticVersion.split(".")
    val versionCodeFromSemVer =
        first.toInt().times(10000).plus(
            second.toInt().times(100).plus(
                third.toInt()
            )
        )

    defaultConfig {
        versionName = semanticVersion
        versionCode = versionCodeFromSemVer
    }
}
