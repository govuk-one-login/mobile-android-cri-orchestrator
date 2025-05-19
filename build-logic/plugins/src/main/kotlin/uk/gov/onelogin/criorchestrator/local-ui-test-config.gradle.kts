package uk.gov.onelogin.criorchestrator

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import uk.gov.onelogin.criorchestrator.extensions.testImplementation
import uk.gov.onelogin.criorchestrator.extensions.testRuntimeOnly
import uk.gov.onelogin.criorchestrator.extensions.uiTestDependencies

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

configure<BaseExtension> {
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // Allows JUnit4 tests to run
    testRuntimeOnly(libs.org.junit.vintage)
    // Allows Robolectric tests to run
    testImplementation(libs.org.junit.junit4)
    testImplementation(libs.org.robolectric.robolectric)

    testImplementation(testFixtures(project(":libraries:compose-utils")))

    uiTestDependencies(libs).forEach(::testImplementation)
}