plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Architecture Module",
        )
        description.set(
            "Common architectural components",
        )
    }
}
