package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity
import android.content.Context
import com.google.android.play.core.ktx.launchReview
import com.google.android.play.core.ktx.requestReview
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class ReviewRequesterImpl
    @Inject
    constructor(
        context: Context,
    ) : ReviewRequester {
        private val manager: ReviewManager = ReviewManagerFactory.create(context)

        override suspend fun requestReview(activity: Activity) {
            val reviewInfo = manager.requestReview()
            manager.launchReview(activity, reviewInfo)
        }
    }
