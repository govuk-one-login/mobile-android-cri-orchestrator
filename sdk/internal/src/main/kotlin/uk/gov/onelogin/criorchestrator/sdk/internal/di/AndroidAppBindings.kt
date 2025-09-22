package uk.gov.onelogin.criorchestrator.sdk.internal.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.AndroidResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.analytics.resources.ResourceProvider
import uk.gov.onelogin.criorchestrator.libraries.androidutils.UriBuilder
import uk.gov.onelogin.criorchestrator.libraries.androidutils.UriBuilderImpl
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@BindingContainer
@ContributesTo(CriOrchestratorAppScope::class)
fun interface AndroidAppBindings {
    @Binds
    fun urlBuilder(impl: UriBuilderImpl): UriBuilder
}

@BindingContainer
@ContributesTo(CriOrchestratorScope::class)
fun interface AndroidBindings {
    @Binds
    fun resourceProvider(impl: AndroidResourceProvider): ResourceProvider
}
