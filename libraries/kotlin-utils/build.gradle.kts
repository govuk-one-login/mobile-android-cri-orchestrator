plugins {
    id("uk.gov.onelogin.criorchestrator.kotlin-lib-config")
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(platform(libs.org.junit.bom))
}

tasks.test {
    useJUnitPlatform()
}

mavenPublishingConfig {
    mavenConfigBlock {
        name.set(
            "GOV.UK One Login CRI Orchestrator Kotlin Utilities Module",
        )
    }
}
