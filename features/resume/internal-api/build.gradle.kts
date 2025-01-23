plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.base-compose-config",
    ).forEach {
        id(it)
    }
}

dependencies {
    implementation(project(":libraries:di"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Resume ID Check Card Internal API",
        )
        description.set(
            """
            The Resume ID Check Card Internal API module contains the interface used for Resume ID
            Check UI component entry points.
            """.trimIndent(),
        )
    }
}
