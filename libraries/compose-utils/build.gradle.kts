plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
    id("uk.gov.onelogin.criorchestrator.local-ui-test-config")
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui.tooling.preview)

    testImplementation(libs.androidx.navigation.compose)
}
