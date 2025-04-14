plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    id("uk.gov.onelogin.criorchestrator.imposter-test-config")
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

    testImplementation(testFixtures(projects.features.config.internalApi))
    testImplementation(testFixtures(projects.features.session.internalApi))
    testImplementation(libs.uk.gov.logging.testdouble)

    testFixturesImplementation(libs.uk.gov.idcheck.repositories.api)
}
