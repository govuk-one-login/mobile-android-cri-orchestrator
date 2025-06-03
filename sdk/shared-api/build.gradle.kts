import uk.gov.onelogin.criorchestrator.extensions.customisePublications

plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
    id("uk.gov.onelogin.criorchestrator.di-config")
}

configure<PublishingExtension> {
    customisePublications {
        artifactId = "sdk-shared-api"
    }
}

dependencies {
    api(projects.libraries.di)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK Shared API",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator SDK Shared API module contains the public-
            facing abstraction that hides the internal Dagger component.
            """.trimIndent(),
        )
    }
}
