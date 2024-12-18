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
    val (major, minor, patch) = semanticVersion.split(".")
    val versionCodeFromSemVer =
        major.toInt().times(10000).plus(
            minor.toInt().times(100).plus(
                patch.toInt().plus(1),
            ),
        )

    defaultConfig {
        versionName = semanticVersion
        versionCode = versionCodeFromSemVer
    }
}
