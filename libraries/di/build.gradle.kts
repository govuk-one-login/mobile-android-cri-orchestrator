plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")

    alias(libs.plugins.metro)
}

dependencies {
    implementation(libs.metro.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dependency Injection Module",
        )
        description.set(
            """
            The Credential Issuer (CRI) Orchestrator Dependency Injection Module contains dependency
            injection scopes used by Metro.
            """.trimIndent(),
        )
    }
}
