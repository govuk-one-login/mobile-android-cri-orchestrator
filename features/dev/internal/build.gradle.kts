plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.uk.gov.networking)
    implementation(projects.features.dev.internalApi)
    implementation(projects.features.dev.publicApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.libraries.di)

    testImplementation(testFixtures(projects.features.config.internalApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dev Internal",
        )
        description.set(
            """
            Internal implementations for the developer menu
            """.trimIndent(),
        )
    }
}
