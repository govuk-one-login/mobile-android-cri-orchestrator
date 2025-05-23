package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import android.app.Activity
import com.google.android.gms.tasks.Tasks
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.gov.logging.testdouble.SystemLogger

class AndroidRequestAppReviewTest {
    private val activity = mock<Activity>()
    private val reviewInfo = mock<ReviewInfo>()
    private val manager: ReviewManager = mock<FakeReviewManager>()
    private val androidRequestAppReview =
        AndroidRequestAppReview(
            activity = activity,
            logger = SystemLogger(),
        )

    @BeforeEach
    fun setUp() {
        whenever(manager.requestReviewFlow()).thenReturn(Tasks.forResult(reviewInfo))
        whenever(manager.launchReviewFlow(activity, reviewInfo)).thenReturn(Tasks.whenAll())
    }

    @Test
    fun `when request a review is called, it launches the prompt`(): Unit =
        runBlocking {
            withMockReviewManagerFactory {
                androidRequestAppReview()
            }

            verify(manager).launchReviewFlow(activity, reviewInfo)
        }

    private suspend fun withMockReviewManagerFactory(block: suspend () -> Unit) =
        Mockito.mockStatic<ReviewManagerFactory>(ReviewManagerFactory::class.java).use { factory ->
            factory.`when`<ReviewManager> { ReviewManagerFactory.create(activity) }.thenReturn(manager)

            block()
        }
}
