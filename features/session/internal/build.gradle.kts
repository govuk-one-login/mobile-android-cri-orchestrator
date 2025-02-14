plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}
dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:di"))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal",
        )
        description.set(
            """
            The CRI Orchestrator Session Internal module contains implementations used for 
            ID Check session logic that are Dagger injected where requested.
            """.trimIndent(),
        )
    }
}
