package uk.gov.onelogin.criorchestrator.features.handback.internal.returntodesktopweb

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ReviewRequesterImplTest {
    private val activity = mock<Activity>()
    private val reviewInfo = mock<ReviewInfo>()
    private val manager: ReviewManager = mock<FakeReviewManager>()
    private val requester = ReviewRequesterImpl(manager)

    @Test
    fun `when request a review is called, it launches the prompt`(): Unit =
        runBlocking {
            val task: Task<ReviewInfo> = Tasks.forResult(reviewInfo)
            doReturn(task).whenever(manager).requestReviewFlow()
            doReturn(Tasks.whenAll()).whenever(manager).launchReviewFlow(activity, reviewInfo)

            requester.requestReview(activity)

            verify(manager).requestReviewFlow()
            verify(manager).launchReviewFlow(activity, reviewInfo)
        }
}
