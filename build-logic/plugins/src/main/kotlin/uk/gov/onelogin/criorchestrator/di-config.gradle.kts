package uk.gov.onelogin.criorchestrator

import com.squareup.anvil.plugin.AnvilExtension
import org.gradle.accessors.dm.LibrariesForLibs
import uk.gov.onelogin.criorchestrator.extensions.diDependencies
import com.google.devtools.ksp.gradle.KspExtension

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.ksp,
    libs.plugins.anvil,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

configure<AnvilExtension> {
    disableComponentMerging = false
    useKsp(
        contributesAndFactoryGeneration = true,
        componentMerging = true,
    )
}

// https://zacsweers.github.io/metro/0.6.3/adoption.html#precursor-steps
configure<KspExtension> {
    arg("dagger.useBindingGraphFix", "enabled")
}

dependencies {
    diDependencies(libs)
}