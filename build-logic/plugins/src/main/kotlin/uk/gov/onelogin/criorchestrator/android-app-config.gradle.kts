package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.setUiConfig
import uk.gov.onelogin.criorchestrator.extensions.ideSupportDependencies
import uk.gov.onelogin.criorchestrator.extensions.setBuildTypes
import uk.gov.onelogin.criorchestrator.extensions.setJavaVersion
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
import uk.gov.onelogin.criorchestrator.extensions.setPackagingConfig

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.android.application,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

listOf(
    "uk.gov.pipelines.android-app-config",
    "uk.gov.onelogin.criorchestrator.code-quality-config",
    "uk.gov.onelogin.criorchestrator.instrumentation-test-config",
    "uk.gov.onelogin.criorchestrator.ui-config",
    "uk.gov.onelogin.criorchestrator.unit-test-config",
    "uk.gov.pipelines.jacoco-app-config",
    "uk.gov.pipelines.sonarqube-module-config",
).forEach {
    project.plugins.apply(it)
}

configure<ApplicationExtension> {
    setUiConfig()
    setJavaVersion()
    setNamespace(project = project)
    setBuildTypes()
    setPackagingConfig()
}

configure<KotlinAndroidProjectExtension> {
    setJavaVersion()
}

dependencies {
    ideSupportDependencies(libs)
}
