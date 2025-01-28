package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import uk.gov.onelogin.criorchestrator.extensions.androidTestDependencies
import uk.gov.onelogin.criorchestrator.extensions.setInstrumentationTestingConfig
import uk.gov.onelogin.criorchestrator.extensions.setJavaVersion
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
import uk.gov.onelogin.criorchestrator.extensions.testDependencies

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    "uk.gov.pipelines.android-lib-config",
    "uk.gov.onelogin.criorchestrator.di-config",
    "uk.gov.onelogin.criorchestrator.publishing-id-config",
    "uk.gov.onelogin.criorchestrator.unit-test-config",
).forEach {
    project.plugins.apply(it)
}

configure<LibraryExtension> {
    setJavaVersion()
    setInstrumentationTestingConfig()
    setNamespace(project = project)
}

configure<KotlinAndroidProjectExtension> {
    setJavaVersion()
}

dependencies {
    androidTestDependencies(libs)
    testDependencies(libs)
}
