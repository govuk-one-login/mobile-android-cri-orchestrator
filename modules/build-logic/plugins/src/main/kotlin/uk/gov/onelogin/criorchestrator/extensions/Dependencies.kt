package uk.gov.onelogin.criorchestrator.extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.accessors.dm.LibrariesForLibs

internal fun DependencyHandlerScope.implementation(
    dependency: Any,
) = dependencies.add("implementation",  dependency)

internal fun DependencyHandlerScope.debugImplementation(
    dependency: Any,
) = dependencies.add("debugImplementation",  dependency)

internal fun DependencyHandlerScope.testImplementation(
    dependency: Any,
) = dependencies.add("testImplementation",  dependency)

internal fun DependencyHandlerScope.testRuntimeOnly(
    dependency: Any,
) = dependencies.add("testRuntimeOnly",  dependency)

internal fun DependencyHandlerScope.androidTestImplementation(
    dependency: Any,
) = dependencies.add("androidTestImplementation",  dependency)

internal fun DependencyHandlerScope.androidTestUtil(
    dependency: Any,
) = dependencies.add("androidTestUtil",  dependency)

internal fun DependencyHandlerScope.uiDependencies(libs: LibrariesForLibs) = listOf(
    libs.androidx.core.ktx,
    libs.androidx.appcompat,
    libs.material,
    libs.androidx.lifecycle.runtime.ktx,
    libs.androidx.activity.compose,
    platform(libs.androidx.compose.bom),
    libs.androidx.ui,
    libs.androidx.ui.graphics,
    libs.androidx.ui.tooling.preview,
    libs.androidx.material3,
    libs.bundles.gov.uk
).forEach {
    implementation(it)
}

internal fun DependencyHandlerScope.testDependencies(libs: LibrariesForLibs) {
    listOf(
        platform(libs.org.junit.bom),
        libs.org.junit.jupiter.api,
        libs.org.junit.jupiter.engine,
    ).forEach {
        testImplementation(it)
    }
    testRuntimeOnly(libs.org.junit.platform.launcher)
}

internal fun DependencyHandlerScope.androidTestDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.androidx.junit,
        libs.androidx.espresso.core,
        platform(libs.androidx.compose.bom),
        libs.androidx.test.core.ktx,
        libs.androidx.ui.test.junit4,
    ).forEach {
        androidTestImplementation(it)
    }
    androidTestUtil(libs.androidx.test.orchestrator)
    debugImplementation(libs.androidx.ui.test.manifest)
}

internal fun DependencyHandlerScope.ideSupportDependencies(libs: LibrariesForLibs) {
    debugImplementation(libs.androidx.ui.tooling)
}
