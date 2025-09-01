package uk.gov.onelogin.criorchestrator

import org.gradle.accessors.dm.LibrariesForLibs
import com.google.devtools.ksp.gradle.KspExtension
import dev.zacsweers.metro.gradle.MetroPluginExtension

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.ksp,
    libs.plugins.metro,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

configure<MetroPluginExtension> {
    enableFullBindingGraphValidation = true
}

// https://zacsweers.github.io/metro/0.6.3/adoption.html#precursor-steps
configure<KspExtension> {
    arg("dagger.useBindingGraphFix", "enabled")
}
