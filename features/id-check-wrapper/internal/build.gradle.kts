plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
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
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)

    testFixturesImplementation(projects.features.session.internalApi)
    testFixturesImplementation(libs.uk.gov.idcheck.repositories.api)
    testFixturesImplementation(libs.uk.gov.idcheck.sdk)
    testFixturesImplementation(libs.uk.gov.idcheck.ui.presentation)
    testFixturesImplementation(projects.features.session.internalApi)
    testFixturesImplementation(testFixtures(projects.features.session.internalApi))

    testImplementation(libs.uk.gov.idcheck.repositories.api)
    testImplementation(libs.uk.gov.idcheck.sdk)
    testImplementation(libs.uk.gov.idcheck.ui.presentation)
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.features.session.internalApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator ID Check Wrapper Internal",
        )
    }
}
