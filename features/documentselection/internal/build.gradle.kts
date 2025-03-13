plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.ui-config",
    ).forEach {
        id(it)
    }
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.uk.gov.logging.api)

    implementation(libs.uk.gov.networking)
    implementation(project(":features:documentselection:internal-api"))
    implementation(project(":libraries:android-utils"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))

    debugImplementation(testFixtures(project(":libraries:android-utils")))

    testImplementation(libs.uk.gov.logging.testdouble)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Document Selection Internal",
        )
        description.set(
            """
            The Resume ID Check Card Internal module contains implementations of UI components and
            their entry points relevant to selecting a document type for proving your identity.
            """.trimIndent(),
        )
    }
}
