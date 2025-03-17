package uk.gov.onelogin.criorchestrator.testwrapper.network

import uk.gov.android.network.auth.AuthenticationProvider
import uk.gov.android.network.auth.AuthenticationResponse
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.android.network.client.KtorHttpClient
import uk.gov.android.network.useragent.UserAgentGeneratorStub

internal fun createHttpClient(): GenericHttpClient = KtorHttpClient(
    userAgentGenerator = UserAgentGeneratorStub("userAgent"),
).apply {
    setAuthenticationProvider(StubAuthenticationProvider())
}

private class StubAuthenticationProvider : AuthenticationProvider {
    override suspend fun fetchBearerToken(scope: String): AuthenticationResponse =
        AuthenticationResponse.Success("token")
}
