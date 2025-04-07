plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(project(":features:resume:internal-api"))
    implementation(project(":libraries:di"))
    implementation(project(":libraries:navigation"))
}
