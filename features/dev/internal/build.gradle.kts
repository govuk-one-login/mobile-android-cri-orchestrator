plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.uk.gov.networking)
    implementation(project(":features:dev:internal-api"))
    implementation(project(":features:dev:public-api"))
    implementation(project(":features:config:public-api"))
    implementation(project(":libraries:di"))

    testImplementation(testFixtures(project(":features:config:internal")))
    testImplementation(testFixtures(project(":features:config:public-api")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Dev Internal",
        )
        description.set(
            """
            Internal implementations for the developer menu
            """.trimIndent(),
        )
    }
}
