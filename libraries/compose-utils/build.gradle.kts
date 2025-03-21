plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui.tooling.preview)
}
