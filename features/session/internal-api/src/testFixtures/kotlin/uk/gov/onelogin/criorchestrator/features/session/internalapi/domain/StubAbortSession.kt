package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

class StubAbortSession(
    var result: AbortSession.Result = AbortSession.Result.Success,
) : AbortSession {
    override suspend fun invoke(): AbortSession.Result = result
}
