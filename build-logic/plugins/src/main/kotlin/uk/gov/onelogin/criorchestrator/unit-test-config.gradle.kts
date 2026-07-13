package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestLogEvent
import uk.gov.onelogin.criorchestrator.extensions.kotlinTestDependencies
import uk.gov.onelogin.criorchestrator.extensions.testImplementation

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

if(project.extensions.findByType<CommonExtension>() != null) {
    configure<CommonExtension> {
        testOptions.apply {
            unitTests.all {
                it.configureTestTask()
            }
        }
    }

    dependencies {
        testImplementation(testFixtures(project(":libraries:testing")))
    }
} else {
    tasks.withType<Test> {
        configureTestTask()
    }
}

dependencies {
    kotlinTestDependencies(libs)
}

private fun Test.configureTestTask() {
    useJUnitPlatform()
    testLogging {
        events = setOf(
            TestLogEvent.SKIPPED,
            TestLogEvent.FAILED,
        )
    }
}

