plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.imposter-test-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(projects.features.config.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.composeUtils)
    implementation(projects.libraries.kotlinUtils)
    implementation(projects.libraries.di)

    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesImplementation(libs.uk.gov.logging.testdouble)
    testFixturesImplementation(libs.uk.gov.networking)
    testFixturesImplementation(projects.features.session.internalApi)
    testFixturesImplementation(testFixtures(projects.features.session.internalApi))

    testImplementation(libs.kotlinx.coroutines)
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(projects.features.config.publicApi)
    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(testFixtures(projects.libraries.analytics))
    testImplementation(testFixtures(projects.features.session.internalApi))
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
