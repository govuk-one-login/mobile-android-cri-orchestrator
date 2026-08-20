plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

// apply(from = rootProject.file("mobile-android-pipelines/buildLogic/gradle/snapshot-test-filter.gradle.kts"))

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.uk.gov.networking)
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
