plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
    id("uk.gov.onelogin.criorchestrator.di-config")
}
dependencies {
    implementation(projects.libraries.di)
    implementation(projects.sdk.sharedApi)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Public API",
        )
    }
}
