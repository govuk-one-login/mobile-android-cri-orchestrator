plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)

    implementation(libs.uk.gov.networking)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.resume.publicApi)
    implementation(projects.features.selectDoc.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)

    testFixturesImplementation(testFixtures(projects.features.session.internalApi))

    testImplementation(testFixtures(projects.libraries.analytics))
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(projects.features.idCheckWrapper.internalApi)
    testImplementation(testFixtures(projects.features.session.internalApi))
    testImplementation(kotlin("test"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Resume ID Check Card Internal",
        )
        description.set(
            """
            The Resume ID Check Card Internal module contains implementations of UI components and
            their entry points relevant the Resume ID Check Card feature.
            """.trimIndent(),
        )
    }
}
