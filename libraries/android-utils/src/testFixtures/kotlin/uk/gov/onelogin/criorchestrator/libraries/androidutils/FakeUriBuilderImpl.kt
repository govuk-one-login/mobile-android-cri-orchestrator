package uk.gov.onelogin.criorchestrator.libraries.androidutils

import java.net.URLEncoder

class FakeUriBuilderImpl : UriBuilder {
    override fun buildUri(
        baseUri: String,
        queryKey: String,
        queryValue: String,
    ): String {
        val encodedQueryValue = URLEncoder.encode(queryValue, "utf-8")
        val separator = if (baseUri.contains('?')) '&' else '?'
        return "${baseUri}${separator}$queryKey=$encodedQueryValue"
    }
}
