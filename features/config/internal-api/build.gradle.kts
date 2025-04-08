plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    api(libs.kotlinx.collections.immutable)
    api(projects.features.config.publicApi)

    implementation(libs.kotlinx.coroutines)

    testImplementation(testFixtures(projects.features.config.publicApi))

    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesApi(testFixtures(projects.features.config.publicApi))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal API",
        )
    }
}
