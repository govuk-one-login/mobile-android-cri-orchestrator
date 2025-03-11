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
    // This module should depend on every other module that contributes to the dagger dependency graph
    implementation(libs.firebase.crashlytics)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.logging.impl)
    implementation(libs.uk.gov.networking)
    implementation(project(":features:config:public-api"))
    implementation(project(":features:resume:internal"))
    implementation(project(":features:resume:internal-api"))
    implementation(project(":features:resume:public-api"))
    implementation(project(":features:session:internal"))
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:android-utils"))
    implementation(project(":libraries:navigation"))
    implementation(project(":sdk:shared-api"))
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
