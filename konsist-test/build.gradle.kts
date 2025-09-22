import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
import uk.gov.pipelines.extensions.BaseExtensions.baseAndroidConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("uk.gov.pipelines.jvm-toolchains")
    id("uk.gov.onelogin.criorchestrator.unit-test-config")
    id("uk.gov.onelogin.criorchestrator.code-quality-config")
}

configure<BaseExtension> {
    baseAndroidConfig(project)
}

configure<LibraryExtension> {
    setNamespace(project = project)
}

// https://docs.konsist.lemonappdev.com/advanced/isolate-konsist-tests
tasks.withType<Test> {
    outputs.upToDateWhen { false }
}

dependencies {
    testImplementation(libs.com.lemonappdev.konsist)
    testImplementation(libs.metro.runtime)
    testFixturesCompileOnly(libs.com.lemonappdev.konsist)
}
