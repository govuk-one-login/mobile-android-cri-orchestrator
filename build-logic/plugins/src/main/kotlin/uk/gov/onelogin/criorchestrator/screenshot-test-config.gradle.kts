package uk.gov.onelogin.criorchestrator

import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.testImplementation
import uk.gov.onelogin.criorchestrator.extensions.testRuntimeOnly

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.app.cash.paparazzi,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

dependencies {
    testImplementation(testFixtures(project(":libraries:screenshot-testing")))
    // Allows JUnit4 tests to run
    testRuntimeOnly(libs.org.junit.vintage)
    // Allows Paparazzi tests to run
    testImplementation(libs.org.junit.junit4)
}