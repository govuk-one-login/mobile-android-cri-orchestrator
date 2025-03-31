import uk.gov.pipelines.config.ApkConfig
import uk.gov.pipelines.emulator.EmulatorConfig
import uk.gov.pipelines.emulator.SystemImageSource

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.anvil) apply false
    alias(libs.plugins.app.cash.paparazzi) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    id("uk.gov.pipelines.android-root-config")
}

buildscript {
    // Github packages publishing configuration
    val githubRepositoryName: String by rootProject.extra("mobile-android-cri-orchestrator")
    val mavenGroupId: String by rootProject.extra("uk.gov.onelogin.criorchestrator")

    val buildLogicDir: String by extra("mobile-android-pipelines/buildLogic")
    val sonarProperties: Map<String, String> by extra(
        mapOf(
            "sonar.projectKey" to "mobile-android-cri-orchestrator",
            "sonar.projectId" to "mobile-android-cri-orchestrator",
        )
    )
    dependencies {
        listOf(
            // https://issuetracker.google.com/issues/380600747
            libs.org.bouncycastle.bcutil.jdk18on,
        ).forEach {
            classpath(it)
        }
    }
}

// https://stackoverflow.com/a/78325449/6449273
gradle.startParameter.excludedTaskNames.addAll(
    gradle.startParameter.taskNames.filter { it.contains("testClasses") }
)

val apkConfig by rootProject.extra(
    object : ApkConfig {
        override val applicationId: String = "uk.gov.onelogin.criorchestrator"
        override val debugVersion: String = "DEBUG_VERSION"
        override val sdkVersions = object : ApkConfig.SdkVersions {
            override val minimum = 29
            override val target = 34
            override val compile = 35
        }
    }
)

val emulatorConfig: EmulatorConfig by extra(
    EmulatorConfig(
        systemImageSources = setOf(
            SystemImageSource.AOSP_ATD
        ),
        androidApiLevels = setOf(33),
        deviceFilters = setOf("Pixel XL"),
    )
)
