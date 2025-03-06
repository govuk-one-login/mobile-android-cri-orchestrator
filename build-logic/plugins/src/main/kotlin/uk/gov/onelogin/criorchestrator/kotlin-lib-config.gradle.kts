package uk.gov.onelogin.criorchestrator

import uk.gov.onelogin.criorchestrator.extensions.disableJavadocGeneration
import kotlin.collections.forEach
import kotlin.collections.listOf

listOf(
    "uk.gov.pipelines.kotlin-lib-config",
    "uk.gov.onelogin.criorchestrator.code-quality-config",
    "uk.gov.onelogin.criorchestrator.publishing-id-config",
).forEach {
    project.plugins.apply(it)
}

// https://govukverify.atlassian.net/browse/DCMAW-11888
// https://github.com/Kotlin/dokka/issues/2956
project.disableJavadocGeneration()
