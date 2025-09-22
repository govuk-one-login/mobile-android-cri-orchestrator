plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    implementation(projects.sdk.sharedApi)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Resume ID Check Card Public API",
        )
        description.set(
            """
            The Resume ID Check Card Public API module contains the Compose composable that functions
            as the single touchpoint between the consuming app and the Resume ID Check Card feature.
            """.trimIndent(),
        )
    }
}
