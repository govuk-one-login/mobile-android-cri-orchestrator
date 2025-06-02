import uk.gov.onelogin.criorchestrator.extensions.customisePublications

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.base-compose-config",
        "uk.gov.onelogin.criorchestrator.id-check-sdk-compat-config",
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
    api(projects.features.config.publicApi)
    api(projects.features.dev.publicApi)
    api(projects.features.idCheckWrapper.publicApi)
    api(projects.features.resume.publicApi)
    api(projects.features.session.publicApi)
    api(projects.sdk.sharedApi)

    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(libs.androidx.activity.compose)
    implementation(projects.sdk.internal)

    testFixturesImplementation(libs.org.mockito.kotlin)
    testFixturesImplementation(libs.uk.gov.logging.api)
    testFixturesImplementation(libs.uk.gov.networking)
    testFixturesImplementation(projects.sdk.internal)

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(projects.features.config.publicApi)
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
