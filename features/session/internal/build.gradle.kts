plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

android {
    // https://github.com/Kotlin/dokka/issues/2956
    tasks
        .matching { task ->
            task.name.contains("javaDocReleaseGeneration", ignoreCase = true) or
                task.name.contains("javaDocDebugGeneration")
        }.configureEach {
            enabled = false
        }
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:android-utils"))
    implementation(project(":libraries:di"))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(project(":libraries:android-utils")))
    testFixturesImplementation(libs.uk.gov.logging.testdouble)
    testFixturesImplementation(libs.uk.gov.networking)
    testFixturesImplementation(project(":features:session:internal-api"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal",
        )
        description.set(
            """
            The CRI Orchestrator Session Internal module contains implementations used for 
            ID Check session logic that are Dagger injected where requested.
            """.trimIndent(),
        )
    }
}
