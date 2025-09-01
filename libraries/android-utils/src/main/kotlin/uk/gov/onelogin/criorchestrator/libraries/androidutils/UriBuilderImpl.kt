package uk.gov.onelogin.criorchestrator.libraries.androidutils

import androidx.core.net.toUri
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@OptIn(ExperimentalCoroutinesApi::class)
@ContributesBinding(CriOrchestratorSingletonScope::class)
class UriBuilderImpl
    @Inject
    constructor() : UriBuilder {
        override fun buildUri(
            baseUri: String,
            queryKey: String,
            queryValue: String,
        ): String =
            baseUri
                .toUri()
                .buildUpon()
                .appendQueryParameter(queryKey, queryValue)
                .build()
                .toString()
    }
