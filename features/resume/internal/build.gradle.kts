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
    implementation(project(":features:resume:internal-api"))
    implementation(project(":features:resume:public-api"))
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:android-utils"))
    implementation(project(":libraries:di"))

    debugImplementation(libs.uk.gov.logging.testdouble)
    releaseCompileOnly(libs.uk.gov.logging.testdouble)
    debugImplementation(testFixtures(project(":libraries:android-utils")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Resume ID Check Card Internal",
        )
        description.set(
            """
            The Resume ID Check Card Internal module contains implementations of UI components and
            their entry points relevant the Resume ID Check Card feature.
            """.trimIndent(),
        )
    }
}
