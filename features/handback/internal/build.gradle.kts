plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)

    implementation(projects.features.config.internalApi)
    implementation(projects.features.error.internalApi)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
    implementation(projects.features.session.internalApi)

    implementation(libs.google.play.review)
    implementation(libs.google.play.review.kotlin)
    implementation(libs.kotlinx.coroutines.play.services)

    testFixturesImplementation(testFixtures(projects.libraries.navigation))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(projects.features.session.internalApi)
    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(testFixtures(projects.features.session.internalApi))
    testImplementation(testFixtures(projects.libraries.analytics))
    testImplementation(testFixtures(projects.libraries.navigation))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Handback Internal",
        )
    }
}
