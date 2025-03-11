plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(project(":libraries:di"))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(project(":features:config:public-api")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal API",
        )
        description.set(
            """
            The Config Public API module contains the configuration store class
            and an interface for providing configuration to the configuration store.
            """.trimIndent(),
        )
    }
}
