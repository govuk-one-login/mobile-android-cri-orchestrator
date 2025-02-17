plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.local-ui-test-config")
}

android {
    // https://github.com/Kotlin/dokka/issues/2956
    tasks.matching { task ->
        task.name.contains("javaDocReleaseGeneration", ignoreCase = true) or
                task.name.contains("javaDocDebugGeneration")
    }.configureEach {
        enabled = false
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:di"))

    testImplementation(libs.uk.gov.logging.testdouble)
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
