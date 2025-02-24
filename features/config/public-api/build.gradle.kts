plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal API",
        )
        // TODO: write meaningful description
        description.set(
            """
            """.trimIndent(),
        )
    }
}
