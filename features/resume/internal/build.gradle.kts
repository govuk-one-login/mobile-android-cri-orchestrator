plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.ui-config",
    ).forEach {
        id(it)
    }
}

dependencies {
    listOf(
        project(":libraries:di"),
        project(":features:resume:internal-api"),
        project(":features:resume:public-api"),
    ).forEach {
        implementation(it)
    }

    api(libs.uk.gov.logging.api)
    api(libs.androidx.lifecycle.viewmodel.compose)
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
