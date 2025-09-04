import uk.gov.onelogin.criorchestrator.extensions.customisePublications

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.id-check-sdk-compat-config",
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
    // This module should depend on every other module that contributes to the dagger dependency graph
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.logging.impl)
    implementation(libs.uk.gov.networking)
    implementation(projects.features.config.internal)
    implementation(projects.features.config.internalApi)
    implementation(projects.features.config.publicApi)
    implementation(projects.features.dev.internal)
    implementation(projects.features.dev.internalApi)
    implementation(projects.features.dev.publicApi)
    implementation(projects.features.error.internal)
    implementation(projects.features.error.internalApi)
    implementation(projects.features.idCheckWrapper.internal)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.idCheckWrapper.publicApi)
    implementation(projects.features.handback.internal)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.resume.internal)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.resume.publicApi)
    implementation(projects.features.session.internal)
    implementation(projects.features.session.internalApi)
    implementation(projects.features.session.publicApi)
    implementation(projects.features.selectDoc.internal)
    implementation(projects.features.selectDoc.internalApi)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.androidUtils)
    implementation(projects.libraries.composeUtils)
    implementation(projects.libraries.kotlinUtils)
    implementation(projects.libraries.navigation)
    implementation(projects.sdk.sharedApi)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator SDK Internal",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator SDK Internal module contains the real Metro
            dependency graph used for the CRI Orchestrator SDK.
            """.trimIndent(),
        )
    }
}
