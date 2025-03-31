plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.analytics-report")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)
    api(libs.uk.gov.idcheck.sdk)

    implementation(libs.uk.gov.networking)
    implementation(project(":features:config:public-api"))
    implementation(project(":features:resume:internal-api"))
    implementation(project(":features:resume:public-api"))
    implementation(project(":features:select-doc:internal-api"))
    implementation(project(":libraries:analytics"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))

    testImplementation(testFixtures(project(":libraries:analytics")))

    testImplementation(libs.uk.gov.logging.testdouble)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Select Document Internal",
        )
        description.set(
            """
            The Select Document Internal module contains implementations of UI components and their 
            entry points relevant to selecting a document type for proving your identity.
            """.trimIndent(),
        )
    }
}
