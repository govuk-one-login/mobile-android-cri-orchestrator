package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity

fun interface ReviewRequester {
    suspend fun requestReview(activity: Activity)
}
