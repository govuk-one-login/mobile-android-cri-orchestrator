package uk.gov.onelogin.criorchestrator

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.onelogin.criorchestrator.extensions.kotlinTestDependencies
import uk.gov.onelogin.criorchestrator.extensions.testImplementation

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

if(project.extensions.findByType<BaseExtension>() != null) {
    configure<BaseExtension> {
        testOptions {
            unitTests.all {
                it.useJUnitPlatform {
                    project.findProperty("includeTags")?.toString()?.let {
                        includeTags(it)
                    }

                    project.findProperty("excludeTags")?.toString()?.let {
                        excludeTags(it)
                    }
                }
                it.testLogging {
                    events = setOf(
                        TestLogEvent.PASSED,
                        TestLogEvent.SKIPPED,
                        TestLogEvent.FAILED,
                    )
                }
            }
        }
    }

    dependencies {
        testImplementation(testFixtures(project(":libraries:testing")))
    }
}

dependencies {
    kotlinTestDependencies(libs)
}

