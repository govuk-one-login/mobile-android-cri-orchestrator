plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.parcelize)
}
dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.networking)
    implementation(projects.libraries.di)

    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesImplementation(projects.features.session.publicApi)
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal API",
        )
        description.set(
            """
            The CRI Orchestrator Session Internal API module contains interfaces used for 
            ID Check session logic.
            """.trimIndent(),
        )
    }
}
