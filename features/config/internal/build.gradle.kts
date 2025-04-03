plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(project(":features:config:internal-api"))
    implementation(project(":libraries:di"))
    testImplementation(project(":features:config:internal-api"))

    testFixturesImplementation(libs.uk.gov.logging.testdouble)
    testFixturesImplementation(project(":features:config:internal-api"))
    testFixturesImplementation(testFixtures(project(":features:config:internal-api")))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(project(":features:config:public-api")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal",
        )
        description.set(
            """
            Internal implementations for configuring the SDK
            """.trimIndent(),
        )
    }
}
