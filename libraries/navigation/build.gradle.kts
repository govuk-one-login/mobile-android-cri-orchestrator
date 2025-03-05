plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    id("uk.gov.onelogin.criorchestrator.base-compose-config")
    id("uk.gov.onelogin.criorchestrator.local-ui-test-config")

    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.kotlinx.serialization.json)
    api(libs.androidx.navigation.compose)

    testImplementation(libs.androidx.compose.material3)
    testImplementation(libs.kotlinx.serialization.json)
}
