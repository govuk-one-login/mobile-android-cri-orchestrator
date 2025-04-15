plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.imposter-test-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(libs.uk.gov.idcheck.sdk)
    implementation(libs.uk.gov.idcheck.ui.presentation)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.error.internalApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
    implementation(libs.uk.gov.networking)
    implementation(libs.uk.gov.logging.api)

    testFixturesImplementation(projects.features.session.internalApi)
    testFixturesImplementation(libs.uk.gov.idcheck.repositories.api)
    testFixturesImplementation(libs.uk.gov.idcheck.sdk)
    testFixturesImplementation(libs.uk.gov.idcheck.ui.presentation)
    testFixturesImplementation(projects.features.session.internalApi)
    testFixturesImplementation(testFixtures(projects.features.session.internalApi))
    implementation(projects.libraries.analytics)

    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(testFixtures(projects.features.session.internalApi))
    testImplementation(libs.uk.gov.idcheck.repositories.api)
    testImplementation(libs.uk.gov.idcheck.sdk)
    testImplementation(libs.uk.gov.idcheck.ui.presentation)
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.libraries.analytics))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator ID Check Wrapper Internal",
        )
    }
}
