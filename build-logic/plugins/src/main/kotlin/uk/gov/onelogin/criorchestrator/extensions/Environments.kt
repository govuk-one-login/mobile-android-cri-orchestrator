package uk.gov.onelogin.criorchestrator.extensions

import com.android.build.api.dsl.ApplicationExtension

fun ApplicationExtension.configureEnvironmentFlavors() =
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

