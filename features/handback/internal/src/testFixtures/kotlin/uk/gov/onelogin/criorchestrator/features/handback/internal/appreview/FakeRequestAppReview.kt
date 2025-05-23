package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

class FakeRequestAppReview : RequestAppReview {
    var hasRequestedReview: Boolean = false
        private set

    override suspend fun invoke() {
        hasRequestedReview = true
    }
}
