package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import uk.gov.onelogin.criorchestrator.extensions.uiDependencies
import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.setAndroidSdkVersions
import uk.gov.onelogin.criorchestrator.extensions.setUiConfig
import uk.gov.onelogin.criorchestrator.extensions.ideSupportDependencies
import uk.gov.onelogin.criorchestrator.extensions.setJavaVersion
import uk.gov.onelogin.criorchestrator.extensions.setPackagingConfig

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.android.application,
    libs.plugins.kotlin.android,
    libs.plugins.compose.compiler,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

listOf(
    "uk.gov.onelogin.criorchestrator.code-quality-config",
    "uk.gov.onelogin.criorchestrator.unit-test-config",
    "uk.gov.onelogin.criorchestrator.instrumentation-test-config",
    "uk.gov.pipelines.jacoco-app-config",
    "uk.gov.pipelines.sonarqube-module-config",
).forEach {
    project.plugins.apply(it)
}

configure<ApplicationExtension> {
    setAndroidSdkVersions()
    setUiConfig()
    setJavaVersion()
    setUiConfig()
    setPackagingConfig()
    val releaseSigningConfig =
        this.signingConfigs.register("releaseSigningConfig") {
            val tmpFilePath = System.getProperty("user.home") + "/work/_temp/keystore/"
            val allFilesFromDir = File(tmpFilePath).listFiles()
            val keyStoreFile = File("${project.rootProject.projectDir}/config/keystore.jks")

            val hasMovedGitRunnerKeystore =
                allFilesFromDir?.first()?.renameTo(keyStoreFile) ?: false

            val logMessage =
                if (hasMovedGitRunnerKeystore) {
                    "Moved keystore file from \"$tmpFilePath\"!"
                } else {
                    "Using existing keystore in \"$keyStoreFile\"!"
                }

            project.logger.lifecycle(logMessage)

            storeFile = keyStoreFile
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = releaseSigningConfig.get()
        }
    }
}

configure<KotlinAndroidProjectExtension> {
    setJavaVersion()
}

dependencies {
    uiDependencies(libs)
    ideSupportDependencies(libs)
}
