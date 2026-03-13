plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    api(libs.uk.gov.securestore)

    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Store Module",
        )
    }
}
