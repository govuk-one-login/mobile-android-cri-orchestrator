plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)

    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(libs.uk.gov.networking)
    implementation(libs.uk.gov.idcheck.sdk)
    implementation(projects.features.config.publicApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.resume.publicApi)
    implementation(projects.features.selectDoc.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
    implementation(projects.features.idCheckWrapper.internalApi)

    testImplementation(testFixtures(projects.libraries.analytics))

    testImplementation(libs.uk.gov.logging.testdouble)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Select Document Internal",
        )
        description.set(
            """
            The Select Document Internal module contains implementations of UI components and their 
            entry points relevant to selecting a document type for proving your identity.
            """.trimIndent(),
        )
    }
}
