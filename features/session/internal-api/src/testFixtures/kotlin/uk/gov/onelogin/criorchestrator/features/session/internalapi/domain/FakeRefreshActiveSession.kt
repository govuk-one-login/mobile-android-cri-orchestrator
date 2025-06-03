package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSession

class FakeRefreshActiveSession(
    var willHaveActiveSession: Boolean = true,
    private val fakeIsSessionResumable: FakeIsSessionResumable? = null,
) : RefreshActiveSession {
    override suspend fun invoke() {
        fakeIsSessionResumable?.value?.value = willHaveActiveSession
    }
}
