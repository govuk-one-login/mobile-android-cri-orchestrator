package uk.gov.onelogin.criorchestrator.features.session.internal

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class StubSessionReader(
    var result: SessionReader.Result =
        SessionReader.Result.IsActive(
            session = Session.createTestInstance(),
        ),
) : SessionReader {
    override suspend fun isActiveSession(): SessionReader.Result = result
}
