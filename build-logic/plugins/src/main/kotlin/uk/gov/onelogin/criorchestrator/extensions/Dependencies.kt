package uk.gov.onelogin.criorchestrator.extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

internal fun DependencyHandlerScope.implementation(
    dependency: Any,
) = dependencies.add("implementation",  dependency)

internal fun DependencyHandlerScope.debugImplementation(
    dependency: Any,
) = dependencies.add("debugImplementation",  dependency)

internal fun DependencyHandlerScope.testImplementation(
    dependency: Any,
) = dependencies.add("testImplementation",  dependency)

internal fun DependencyHandlerScope.testFixturesImplementation(
    dependency: Any,
) = dependencies.add("testFixturesImplementation",  dependency)

internal fun DependencyHandlerScope.testRuntimeOnly(
    dependency: Any,
) = dependencies.add("testRuntimeOnly",  dependency)

internal fun DependencyHandlerScope.androidTestImplementation(
    dependency: Any,
) = dependencies.add("androidTestImplementation",  dependency)

internal fun DependencyHandlerScope.androidTestUtil(
    dependency: Any,
) = dependencies.add("androidTestUtil",  dependency)

internal fun DependencyHandlerScope.ksp(
    dependency: Any,
) = dependencies.add("ksp", dependency)

internal fun DependencyHandlerScope.lintChecks(
    dependency: Any
) = dependencies.add("lintChecks", dependency)

internal fun DependencyHandlerScope.project(
    path: String
) = dependencies.project(mapOf("path" to path))

internal fun DependencyHandlerScope.diDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.dagger
    ).forEach {
        implementation(it)
    }
    ksp(libs.dagger.compiler)
}

internal fun DependencyHandlerScope.baseComposeDependencies(libs: LibrariesForLibs) = listOf(
    platform(libs.androidx.compose.bom),
    libs.androidx.ui,
    libs.kotlinx.collections.immutable,
).forEach {
    implementation(it)
    testFixturesImplementation(it)
}

internal fun DependencyHandlerScope.uiDependencies(libs: LibrariesForLibs) = listOf(
    libs.androidx.appcompat,
    libs.material,
    libs.androidx.lifecycle.runtime.ktx,
    libs.androidx.lifecycle.viewmodel.compose,
    libs.androidx.ui.graphics,
    libs.androidx.ui.tooling.preview,
    libs.androidx.material3,
    libs.bundles.uk.gov.ui,
    project(":libraries:compose-utils"),
).forEach {
    implementation(it)
}

internal fun DependencyHandlerScope.kotlinTestDependencies(libs: LibrariesForLibs) {
    testFixturesImplementation(libs.kotlinx.coroutines)
    testFixturesImplementation(libs.kotlinx.coroutines.test)
    testFixturesImplementation(libs.org.mockito.kotlin)

    testImplementation(kotlin("test"))
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.kotlinx.coroutines)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.org.junit.jupiter.api)
    testImplementation(libs.org.junit.jupiter.engine)
    testImplementation(libs.org.mockito.kotlin)
    testImplementation(platform(libs.org.junit.bom))
    testRuntimeOnly(libs.org.junit.platform.launcher)
}

internal fun DependencyHandlerScope.composeTestDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.app.cash.molecule.runtime,
        libs.app.cash.turbine,
    ).forEach(
        ::testImplementation
    )
}

internal fun DependencyHandlerScope.androidTestDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.androidx.junit,
        libs.androidx.test.core.ktx,
        libs.androidx.test.runner,
    ).forEach {
        androidTestImplementation(it)
    }
    androidTestUtil(libs.androidx.test.orchestrator)
}

fun DependencyHandlerScope.uiTestDependencies(libs: LibrariesForLibs) =
    listOf(
        libs.androidx.test.core.ktx,
        libs.androidx.espresso.core,
        libs.androidx.ui.test.junit4,
        platform(libs.androidx.compose.bom),
        libs.androidx.compose.ui.test.manifest
    )

internal fun DependencyHandlerScope.ideSupportDependencies(libs: LibrariesForLibs) {
    debugImplementation(libs.androidx.ui.tooling)
}

internal fun DependencyHandlerScope.imposterTestDependencies(libs: LibrariesForLibs) {
    testImplementation(libs.bundles.imposter)
}
