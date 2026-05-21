package uk.gov.onelogin.criorchestrator

import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.abi.AbiValidationExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

configure<KotlinProjectExtension> {
    @OptIn(ExperimentalAbiValidation::class)
    configure<AbiValidationExtension> {
        enabled = true

        filters {
            exclude {
                byNames.addAll(
                    "**.internal.**",
                    "**.internalapi.**",
                    "metro.**",
                )
            }
        }
    }
}

