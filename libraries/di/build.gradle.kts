plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {

    implementation(libs.anvil.gradle.plugin)
    implementation(libs.dagger)
    implementation(libs.firebase.crashlytics)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.logging.impl)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dependency Injection Module",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator Dependency Injection Module contains dependency
            injection scopes used by Dagger and Anvil.
            """.trimIndent(),
        )
    }
}
