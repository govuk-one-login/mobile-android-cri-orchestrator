plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(projects.features.idCheckWrapper.internalApi)
    implementation(projects.features.session.internalApi)
    implementation(project(":features:resume:internal-api"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))
}
