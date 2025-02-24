plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(project(":features:config:public-api"))
    implementation(project(":libraries:di"))

    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(testFixtures(project(":features:config:public-api")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal",
        )
        // TODO: write meaningful description
        description.set(
            """
            """.trimIndent(),
        )
    }
}
