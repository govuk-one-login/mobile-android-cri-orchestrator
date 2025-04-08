plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(projects.libraries.di)

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.features.config.publicApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Public API",
        )
        description.set(
            """
            The Config Public API module provides tools for configuring the SDK.
            """.trimIndent(),
        )
    }
}
