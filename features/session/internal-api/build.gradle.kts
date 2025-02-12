plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}
dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.networking)
    implementation(project(":libraries:di"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal API",
        )
        // TODO: write meaningful description
        description.set(
            """
            """.trimIndent(),
        )
    }
}
