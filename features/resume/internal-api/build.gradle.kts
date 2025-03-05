plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))
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
