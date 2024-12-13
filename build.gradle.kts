import uk.gov.pipelines.config.ApkConfig
import uk.gov.pipelines.emulator.EmulatorConfig
import uk.gov.pipelines.emulator.SystemImageSource

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("uk.gov.pipelines.android-root-config")
}

buildscript {
    // Github packages publishing configuration
    val githubRepositoryName: String by rootProject.extra("mobile-android-cri-orchestrator")
    val mavenGroupId: String by rootProject.extra("uk.gov.criorchestrator")

    // Sonar configuration
    val buildLogicDir: String by extra("mobile-android-pipelines/buildLogic")
    val sonarProperties: Map<String, String> by extra(
        mapOf(
            "sonar.projectKey" to "mobile-android-cri-orchestrator",
            "sonar.projectId" to "mobile-android-cri-orchestrator",
        )
    )
}

val apkConfig by rootProject.extra(
    object: ApkConfig {
        override val applicationId: String = "uk.gov.criorchestrator"
        override val debugVersion: String = "DEBUG_VERSION"
        override val sdkVersions = object: ApkConfig.SdkVersions {
            override val minimum = 29
            override val target = 33
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


/**
 * Create a convenience task for extracting all locally published artifacts into a single folder.
 * This makes moving the artifacts for local development purposes easier.
 */
tasks.register<Copy>("publishAllDefaultPublicationsToLocalBuildRepository") {
    val localPublishTasks: List<Task> =
        subprojects.mapNotNull { subProject ->
            subProject.tasks.findByName(
                "publishDefaultPublicationToLocalBuildRepository",
            )
        }

    this.dependsOn.addAll(localPublishTasks)

    from(project.layout.projectDirectory.dir("modules"))
    include("**/build/repo/**")
    into(project.layout.buildDirectory)
    eachFile {
        val segments = this.relativePath.segments.asList()
        val cutoff = if (segments[0] == "cri-orchestrator") 3 else 2
        this.path = segments.takeLast(segments.size - cutoff).joinToString("/")
    }
    includeEmptyDirs = false
}

