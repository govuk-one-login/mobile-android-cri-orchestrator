plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
}

dependencies {
    implementation(project(":sdk:shared-api"))
    implementation(project(":features:dev:internal-api"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dev Public API",
        )
    }
}
