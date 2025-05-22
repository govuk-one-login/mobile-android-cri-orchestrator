plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.local-ui-test-config")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(projects.libraries.di)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Android Utilities Module",
        )
    }
}
