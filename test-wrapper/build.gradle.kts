import com.android.build.api.dsl.ApplicationExtension
import uk.gov.onelogin.criorchestrator.extensions.configureEnvironmentFlavors
import uk.gov.onelogin.criorchestrator.extensions.setApplicationId

plugins {
    id("uk.gov.onelogin.criorchestrator.android-app-config")
    alias(testwrapperlibs.plugins.firebase.crashlytics)
    alias(testwrapperlibs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization) apply true
    alias(libs.plugins.ksp)
    alias(testwrapperlibs.plugins.hilt.gradle)
    id("uk.gov.onelogin.criorchestrator.id-check-sdk-compat-config")
}

configure<ApplicationExtension> {
    setApplicationId(suffix = ".testwrapper")
    configureEnvironmentFlavors()
    testFixtures {
        // Disable test fixtures in this app module while they aren't needed
        // https://issuetracker.google.com/issues/368175116
        enable = false
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.logging.impl)
    implementation(libs.uk.gov.logging.testdouble)
    implementation(libs.uk.gov.networking)
    implementation(platform(testwrapperlibs.firebase.bom))
    implementation(projects.features.config.publicApi)
    implementation(projects.features.dev.publicApi)
    implementation(projects.sdk.publicApi)
    implementation(testwrapperlibs.firebase.analytics)
    implementation(testwrapperlibs.firebase.crashlytics)
    implementation(testwrapperlibs.hilt.android)
    implementation(testwrapperlibs.uk.gov.idcheck.hilt.config)

    ksp(testwrapperlibs.hilt.compiler)

    testImplementation(platform(libs.org.junit.bom))
    testImplementation(testFixtures(projects.sdk.publicApi))
}
