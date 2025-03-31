plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)

    implementation(projects.features.handback.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)

    testImplementation(testFixtures(project(":libraries:analytics")))

    testImplementation(libs.uk.gov.logging.testdouble)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Handback Internal",
        )
    }
}
