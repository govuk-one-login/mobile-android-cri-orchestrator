plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
}

dependencies {
    implementation(libs.metro.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
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
