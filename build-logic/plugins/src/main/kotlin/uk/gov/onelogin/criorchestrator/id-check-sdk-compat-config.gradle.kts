package uk.gov.onelogin.criorchestrator

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs

//https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

listOf(
    "kotlin-kapt"
).forEach {
    project.plugins.apply(it)
}

project.extensions.findByType<LibraryExtension>() ?:
    project.extensions.findByType<ApplicationExtension>() ?: error("no Android extension")

project.extensions.findByType<LibraryExtension>()?.run {
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

project.extensions.findByType<ApplicationExtension>()?.run {
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

