plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
}

dependencies {
    implementation(project(":libraries:di"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dev Menu Internal API",
        )
    }
}
