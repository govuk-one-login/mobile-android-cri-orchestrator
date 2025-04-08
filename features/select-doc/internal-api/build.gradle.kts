plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(projects.features.resume.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
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
