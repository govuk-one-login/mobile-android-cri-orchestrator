plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.uk.gov.idcheck.repositories.api)
    implementation(libs.uk.gov.idcheck.ui.presentation)
    implementation(project(":features:resume:internal-api"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))
}
