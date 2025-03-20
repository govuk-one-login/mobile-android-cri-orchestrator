plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
    id("uk.gov.onelogin.criorchestrator.ui-config")
}

dependencies {
    api(libs.androidx.navigation.compose)

    testImplementation(libs.androidx.compose.material3)
}
