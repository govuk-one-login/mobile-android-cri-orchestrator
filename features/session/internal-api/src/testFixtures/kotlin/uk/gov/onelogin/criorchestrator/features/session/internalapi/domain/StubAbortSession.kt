package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.delay

class StubAbortSession(
    var result: AbortSession.Result = AbortSession.Result.Success,
    var delay: Long = 0L,
) : AbortSession {
    override suspend fun invoke(): AbortSession.Result {
        delay(delay)
        return result
    }
}
