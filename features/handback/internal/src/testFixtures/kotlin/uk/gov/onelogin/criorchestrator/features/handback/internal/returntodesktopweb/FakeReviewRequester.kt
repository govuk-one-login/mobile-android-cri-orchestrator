package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity

class FakeReviewRequester : ReviewRequester {
    var requestedReview = false
        private set

    override suspend fun requestReview(activity: Activity) {
        requestedReview = true
    }
}
