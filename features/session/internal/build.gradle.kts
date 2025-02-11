plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal",
        )
        // TODO: write meaningful description
        description.set(
            """
            """.trimIndent(),
        )
    }
}
