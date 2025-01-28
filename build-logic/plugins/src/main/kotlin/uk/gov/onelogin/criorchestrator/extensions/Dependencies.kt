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

internal fun DependencyHandlerScope.ksp(
    dependency: Any,
) = dependencies.add("ksp", dependency)

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
).forEach {
    implementation(it)
}

internal fun DependencyHandlerScope.uiDependencies(libs: LibrariesForLibs) = listOf(
    libs.androidx.appcompat,
    libs.material,
    libs.androidx.lifecycle.runtime.ktx,
    libs.androidx.lifecycle.viewmodel.compose,
    libs.androidx.activity.compose,
    libs.androidx.ui.graphics,
    libs.androidx.ui.tooling.preview,
    libs.androidx.material3,
    libs.bundles.gov.uk,
).forEach {
    implementation(it)
}

internal fun DependencyHandlerScope.testDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.kotlinx.coroutines.test,
        libs.org.junit.jupiter.api,
        libs.org.junit.jupiter.engine,
        libs.uk.gov.logging.testdouble,
        platform(libs.org.junit.bom),
    ).forEach {
        testImplementation(it)
    }
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

internal fun DependencyHandlerScope.androidUiTestDependencies(libs: LibrariesForLibs) {
    listOf(
        libs.androidx.espresso.core,
        libs.androidx.ui.test.junit4,
        platform(libs.androidx.compose.bom),
    ).forEach {
        androidTestImplementation(it)
    }
    debugImplementation(libs.androidx.ui.test.manifest)
}

internal fun DependencyHandlerScope.ideSupportDependencies(libs: LibrariesForLibs) {
    debugImplementation(libs.androidx.ui.tooling)
}
