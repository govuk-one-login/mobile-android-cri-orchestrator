plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(projects.features.resume.internalApi)
    implementation(projects.libraries.di)
    implementation(projects.libraries.navigation)
}
