package uk.gov.onelogin.criorchestrator

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import uk.gov.onelogin.criorchestrator.extensions.setJavaVersion
import kotlin.collections.forEach
import kotlin.collections.listOf

listOf(
    "uk.gov.pipelines.kotlin-lib-config",
    "uk.gov.onelogin.criorchestrator.code-quality-config",
    "uk.gov.onelogin.criorchestrator.publishing-id-config",
).forEach {
    project.plugins.apply(it)
}

configure<KotlinJvmProjectExtension> {
    setJavaVersion()
}

configure<JavaPluginExtension> {
    setJavaVersion()
}
