package uk.gov.onelogin.criorchestrator

import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.imposterTestDependencies

val libs = the<LibrariesForLibs>()

dependencies {
    imposterTestDependencies(libs)
}
