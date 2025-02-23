plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.base-compose-config",
    ).forEach {
        id(it)
    }
}

dependencies {
    listOf(
        project(":sdk:shared-api"),
        project(":features:resume:internal-api"),
    ).forEach {
        implementation(it)
    }
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
