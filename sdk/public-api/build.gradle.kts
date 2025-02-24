import uk.gov.onelogin.criorchestrator.extensions.customisePublications

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.base-compose-config",
    ).forEach {
        id(it)
    }
}

configure<PublishingExtension> {
    customisePublications {
        artifactId = "sdk"
    }
}

dependencies {
    api(project(":features:config:public-api"))
    api(project(":features:resume:public-api"))
    api(project(":sdk:shared-api"))

    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(project(":sdk:internal"))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(project(":features:config:internal"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK Public API",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator coordinates identity proofing capability.
            This module contains the public API used to interface with the CRI Orchestrator
            """.trimIndent(),
        )
    }
}
