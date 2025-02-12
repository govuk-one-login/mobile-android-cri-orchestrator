plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}
dependencies {
    implementation(project(":libraries:di"))
    implementation(project(":features:session:internal-api"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal",
        )
        // TODO: write meaningful description
        description.set(
            """
            """.trimIndent(),
        )
    }
}
