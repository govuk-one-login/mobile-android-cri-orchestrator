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
    listOf(
        libs.firebase.analytics,
        libs.firebase.crashlytics,
        libs.uk.gov.logging.api,
        libs.uk.gov.logging.impl,
        libs.uk.gov.logging.testdouble,
        libs.uk.gov.networking,
        libs.androidx.activity.compose,
        project(":sdk:public-api"),
        project(":sdk:shared-api"),
        platform(libs.firebase.bom),
    ).forEach {
        implementation(it)
    }

    listOf(
        platform(libs.org.junit.bom),
    ).forEach(::testImplementation)
}
