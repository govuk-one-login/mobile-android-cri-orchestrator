import com.android.build.api.dsl.LibraryExtension
import uk.gov.onelogin.criorchestrator.extensions.configureAllEnvironmentFlavors

plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.hilt.gradle)
}

configure<LibraryExtension> {
    configureAllEnvironmentFlavors()
    testFixtures {
        // Disable test fixtures in this app module while they aren't needed
        // https://issuetracker.google.com/issues/368175116
        enable = false
    }
}

private fun DependencyHandlerScope.devImplementation(
    dependency: Provider<MinimalExternalModuleDependency>
) = "devImplementation"(dependency)

dependencies {
    devImplementation(libs.uk.gov.idcheck.network.testdouble)

    implementation(libs.uk.gov.idcheck.network.api)
    implementation(libs.hilt.android)
    implementation(libs.ktor.client.android)

    ksp(libs.hilt.compiler)
}