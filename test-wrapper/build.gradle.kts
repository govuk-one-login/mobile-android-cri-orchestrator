import com.android.build.api.dsl.ApplicationExtension
import uk.gov.onelogin.criorchestrator.extensions.configureEnvironmentFlavors
import uk.gov.onelogin.criorchestrator.extensions.setApplicationId

plugins {
    id("uk.gov.onelogin.criorchestrator.android-app-config")
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
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
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.logging.impl)
    implementation(libs.uk.gov.logging.testdouble)
    implementation(libs.uk.gov.networking)
    implementation(platform(libs.firebase.bom))
    implementation(project(":features:config:public-api"))
    implementation(project(":sdk:public-api"))
    implementation(project(":sdk:shared-api"))

    testImplementation(platform(libs.org.junit.bom))
}
