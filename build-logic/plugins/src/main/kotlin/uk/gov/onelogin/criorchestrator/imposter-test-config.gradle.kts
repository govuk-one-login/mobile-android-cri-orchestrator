package uk.gov.onelogin.criorchestrator

import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.imposterTestDependencies

val libs = the<LibrariesForLibs>()

tasks.withType<Test> {
    systemProperty(
        "uk.gov.onelogin.criorchestrator.imposterConfigDir",
        rootProject.file("config/imposter/")
    )
}

dependencies {
    imposterTestDependencies(libs)
}
