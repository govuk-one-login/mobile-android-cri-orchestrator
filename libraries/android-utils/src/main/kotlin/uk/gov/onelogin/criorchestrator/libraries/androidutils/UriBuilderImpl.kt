package uk.gov.onelogin.criorchestrator.libraries.androidutils

import androidx.core.net.toUri
import dev.zacsweers.metro.Inject

@Inject
class UriBuilderImpl : UriBuilder {
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
