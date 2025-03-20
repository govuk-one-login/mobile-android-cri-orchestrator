plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.uk.gov.logging.api)
    implementation(libs.uk.gov.networking)
    implementation(project(":features:config:public-api"))
    implementation(project(":features:session:internal-api"))
    implementation(project(":libraries:analytics"))
    implementation(project(":libraries:kotlin-utils"))
    implementation(project(":libraries:di"))

    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesImplementation(libs.uk.gov.logging.testdouble)
    testFixturesImplementation(libs.uk.gov.networking)
    testFixturesImplementation(project(":features:session:internal-api"))

    testImplementation(libs.kotlinx.coroutines)
    testImplementation(libs.uk.gov.logging.testdouble)
    testImplementation(project(":features:config:public-api"))
    testImplementation(testFixtures(project(":features:config:internal")))
    testImplementation(testFixtures(project(":libraries:analytics")))

    listOf(
        libs.imposter.config.parser,
        libs.imposter.main,
        libs.imposter.openapi.plugin,
    ).forEach {
        testImplementation(it) {
            exclude(group = "javax.validation", module = "validation-api")
            exclude(group = "io.swagger", module = "swagger-parser-safe-url-resolver")
        }
        testFixturesImplementation(it) {
            exclude(group = "javax.validation", module = "validation-api")
            exclude(group = "io.swagger", module = "swagger-parser-safe-url-resolver")
        }
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Session Internal",
        )
        description.set(
            """
            The CRI Orchestrator Session Internal module contains implementations used for 
            ID Check session logic that are Dagger injected where requested.
            """.trimIndent(),
        )
    }
}
