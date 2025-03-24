plugins {
    listOf(
        "uk.gov.onelogin.criorchestrator.android-lib-config",
        "uk.gov.onelogin.criorchestrator.local-ui-test-config",
    ).forEach {
        id(it)
    }
}

dependencies {
    implementation(libs.uk.gov.idcheck.sdk)
    implementation(project(":libraries:di"))
}
