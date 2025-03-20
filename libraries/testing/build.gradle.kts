plugins {
    id("uk.gov.onelogin.criorchestrator.android-lib-config")
}

dependencies {
    testFixturesImplementation(libs.kotlinx.coroutines.test)
    testFixturesImplementation(libs.org.junit.junit4)
    testFixturesImplementation(libs.org.junit.jupiter.api)
    testFixturesImplementation(libs.uk.gov.logging.api)
    testFixturesImplementation(libs.uk.gov.networking)
    testFixturesImplementation(platform(libs.org.junit.bom))

    testFixturesImplementation(libs.bundles.imposter) {
        // Imposter seems to use dependencies and dependency versions that conflict with existing dependencies in the project.
        // This seems to only be an issue in this test fixtures source set. Resolved by excluding the relevant dependencies below.
        exclude(group = "jakarta.validation", module = "jakarta.validation-api")
        exclude(group = "io.swagger", module = "swagger-parser-safe-url-resolver")
        // This groovy dependency is causing issues with Jacoco transforms. Resolved by excluding.
        exclude(group = "org.apache.groovy", module = "groovy")
    }
}

android {
    packaging {
        resources.excludes += setOf (
            "META-INF/ASL-2.0.txt",
            "META-INF/INDEX.LIST",
            "META-INF/LGPL-3.0.txt",
            "META-INF/NOTICE.md",
            "META-INF/config-resolver.properties",
            "META-INF/groovy-release-info.properties",
            "META-INF/io.netty.versions.properties",
            "META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat",
            "draftv3/schema",
            "draftv4/schema",
        )
    }
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Testing",
        )
    }
}
