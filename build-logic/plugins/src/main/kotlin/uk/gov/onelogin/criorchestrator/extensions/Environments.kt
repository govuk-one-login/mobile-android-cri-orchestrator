package uk.gov.onelogin.criorchestrator.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

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

fun LibraryExtension.configureAllEnvironmentFlavors() =
    productFlavors {
        flavorDimensions.add("env")

        listOf(
            "build",
            "dev",
            "integration",
            "production",
            "staging",
        ).forEach { flavourString ->
            create(flavourString) {
                dimension = "env"
            }
        }
    }
