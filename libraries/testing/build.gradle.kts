plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    testFixturesImplementation(platform(libs.org.junit.bom))
    testFixturesImplementation(libs.org.junit.jupiter.api)
    testFixturesImplementation(libs.kotlinx.coroutines.test)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Testing",
        )
    }
}
