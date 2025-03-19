import com.android.build.gradle.LibraryExtension

plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.local-ui-test-config",
    ).forEach {
        id(it)
    }
}

configure<LibraryExtension> {
    testFixtures {
        androidResources = true
    }
}

dependencies {
    listOf(
        libs.androidx.appcompat,
        libs.uk.gov.logging.api,
        project(":libraries:di"),
    ).forEach {
        implementation(it)
    }
}
