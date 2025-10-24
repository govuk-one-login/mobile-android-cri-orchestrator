package uk.gov.onelogin.criorchestrator

import dev.zacsweers.metro.gradle.MetroPluginExtension
import org.gradle.accessors.dm.LibrariesForLibs

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    libs.plugins.metro,
).forEach {
    project.plugins.apply(it.get().pluginId)
}

configure<MetroPluginExtension> {
    contributesAsInject = true
    enableFullBindingGraphValidation = true
}
