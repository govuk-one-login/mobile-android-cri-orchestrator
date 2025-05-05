plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(libs.uk.gov.idcheck.sdk)
    implementation(libs.uk.gov.idcheck.ui.presentation)
    implementation(projects.features.error.internalApi)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.idCheckWrapper.internal)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)

    testFixturesImplementation(libs.uk.gov.idcheck.repositories.api)
    testFixturesImplementation(libs.uk.gov.logging.api)
    testFixturesImplementation(projects.features.idCheckWrapper.internal)
    testFixturesImplementation(testFixtures(projects.features.idCheckWrapper.internal))
    testFixturesImplementation(testFixtures(projects.features.config.internalApi))
    testFixturesImplementation(testFixtures(projects.features.config.publicApi))
    testFixturesImplementation(testFixtures(projects.features.session.internalApi))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(projects.libraries.analytics))
    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(testFixtures(projects.features.idCheckWrapper.internal))
    testImplementation(testFixtures(projects.features.session.internalApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator ID Check Wrapper Internal UI",
        )
    }
}
