plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.features.handback.internalApi)
    implementation(projects.features.error.internalApi)
    implementation(projects.features.config.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
    implementation(libs.uk.gov.networking)
    implementation(libs.uk.gov.logging.api)

    testImplementation(testFixtures(projects.features.session.internalApi))
    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(libs.kotlinx.coroutines)

    testFixturesImplementation(libs.uk.gov.idcheck.repositories.api)

    testImplementation(libs.bundles.imposter) {
        // Imposter seems to use dependencies and dependency versions that conflict with existing dependencies in the project.
        // This seems to only be an issue in this test fixtures source set. Resolved by excluding the relevant dependencies below.
        exclude(group = "jakarta.validation", module = "jakarta.validation-api")
        exclude(group = "io.swagger", module = "swagger-parser-safe-url-resolver")
        // This groovy dependency is causing issues with Jacoco transforms. Resolved by excluding.
//        exclude(group = "org.apache.groovy", module = "groovy")
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator ID Check Wrapper Internal",
        )
    }
}
