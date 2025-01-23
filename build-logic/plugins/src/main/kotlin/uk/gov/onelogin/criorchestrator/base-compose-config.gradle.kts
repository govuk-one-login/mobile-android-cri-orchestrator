package uk.gov.onelogin.criorchestrator

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import uk.gov.onelogin.criorchestrator.extensions.baseComposeDependencies
import uk.gov.onelogin.criorchestrator.extensions.composeTestDependencies
import kotlin.collections.forEach
import kotlin.collections.listOf

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.compose.compiler
).forEach {
    project.plugins.apply(it.get().pluginId)
}

configure<BaseExtension> {
    testOptions {
        // https://github.com/cashapp/molecule?tab=readme-ov-file#testing
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    baseComposeDependencies(libs)
    composeTestDependencies(libs)
}
