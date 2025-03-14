plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
}

dependencies {
    implementation(libs.kotlinx.coroutines)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Kotlin Utilities Module",
        )
    }
}
