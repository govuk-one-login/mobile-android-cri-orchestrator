package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.tasks.await
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class ReviewRequesterImpl
    @Inject
    constructor(
        private val manager: ReviewManager,
    ) : ReviewRequester {
        override suspend fun requestReview(activity: Activity) {
            val reviewInfo = manager.requestReviewFlow().await()
            manager.launchReviewFlow(activity, reviewInfo).await()
        }
    }
