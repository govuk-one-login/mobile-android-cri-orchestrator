package uk.gov.onelogin.criorchestrator.libraries.androidutils

fun interface UriBuilder {
    fun buildUri(
        baseUri: String,
        queryKey: String,
        queryValue: String,
    ): String
}
