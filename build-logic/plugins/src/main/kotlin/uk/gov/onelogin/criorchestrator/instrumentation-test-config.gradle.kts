package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.androidTestDependencies
import uk.gov.onelogin.criorchestrator.extensions.androidTestImplementation
import uk.gov.onelogin.criorchestrator.extensions.setInstrumentationTestingConfig
import uk.gov.onelogin.criorchestrator.extensions.uiTestDependencies

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    "uk.gov.pipelines.emulator-config",
).forEach {
    project.plugins.apply(it)
}

configure<ApplicationExtension> {
    setInstrumentationTestingConfig()
}

dependencies {
    androidTestDependencies(libs)
    uiTestDependencies(libs).forEach(::androidTestImplementation)
}
