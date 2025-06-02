package uk.gov.onelogin.criorchestrator

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import uk.gov.onelogin.criorchestrator.extensions.disableJavadocGeneration
import uk.gov.onelogin.criorchestrator.extensions.setJavaVersion
import kotlin.collections.forEach
import kotlin.collections.listOf

listOf(
    "uk.gov.pipelines.kotlin-lib-config",
    "uk.gov.onelogin.criorchestrator.code-quality-config",
    "uk.gov.onelogin.criorchestrator.publishing-id-config",
    "java-test-fixtures",
    "uk.gov.onelogin.criorchestrator.unit-test-config",
).forEach {
    project.plugins.apply(it)
}

// https://govukverify.atlassian.net/browse/DCMAW-11888
// https://github.com/Kotlin/dokka/issues/2956
project.disableJavadocGeneration()

configure<KotlinJvmProjectExtension> {
    setJavaVersion()
}

configure<JavaPluginExtension> {
    setJavaVersion()
}

// Since pure Kotlin/Java modules don't have awareness of build variants, the tests for these
// modules won't run when `testDebugUnitTest` is run from root project level. Creating this task
// enables the tests for pure Kotlin/Java modules to run when `testDebugUnitTest` is called.
tasks.register<Test>("testDebugUnitTest") {
    this.dependsOn(project.tasks.getByName("test"))
}
