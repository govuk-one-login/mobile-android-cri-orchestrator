plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
}

dependencies {
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Select Document Internal API",
        )
        description.set(
            """
            The Select Document API module contains the interface used for selecting a document type
            to prove your identity.
            """.trimIndent(),
        )
    }
}
