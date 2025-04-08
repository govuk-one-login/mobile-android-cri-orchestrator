plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(projects.features.resume.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
}
