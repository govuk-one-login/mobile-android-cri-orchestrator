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
        artifactId = "sdk-internal"
    }
}

dependencies {
    listOf(
        libs.uk.gov.networking,
        libs.uk.gov.logging.api,
        project(":sdk:shared-api"),
        project(":features:resume:internal"),
        project(":features:resume:internal-api"),
        project(":features:resume:public-api"),
    ).forEach {
        implementation(it)
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK Internal",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator SDK Internal module contains the real Dagger
            component used for the CRI Orchestrator SDK, and functions to instantiate it.
            """.trimIndent(),
        )
    }
}
