plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    api(libs.kotlinx.collections.immutable)
    api(project(":features:config:public-api"))

    implementation(libs.kotlinx.coroutines)

    testImplementation(testFixtures(project(":features:config:public-api")))

    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesApi(testFixtures(project(":features:config:public-api")))
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Config Internal API",
        )
    }
}
