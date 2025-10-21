package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.tasks.await
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger

@Inject
class AndroidRequestAppReview(
    private val activity: Activity,
    private val logger: Logger,
) : RequestAppReview,
    LogTagProvider {
    override suspend fun invoke() {
        val reviewManager = ReviewManagerFactory.create(activity)
        val reviewInfo = reviewManager.requestReviewFlow().await()
        logger.info(tag, "Launching app review flow")
        reviewManager.launchReviewFlow(activity, reviewInfo).await()
    }
}
