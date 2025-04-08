import uk.gov.onelogin.criorchestrator.extensions.customisePublications

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
    ).forEach {
        id(it)
    }
}

configure<PublishingExtension> {
    customisePublications {
        artifactId = "sdk-shared-api"
    }
}

dependencies {
    listOf(
        projects.libraries.di,
    ).forEach {
        api(it)
    }
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
