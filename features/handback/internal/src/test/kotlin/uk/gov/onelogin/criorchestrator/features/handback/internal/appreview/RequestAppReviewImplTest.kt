package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey

class RequestAppReviewImplTest {
    private val configStore = FakeConfigStore()
    private val androidRequestAppReview = mock<AndroidRequestAppReview>()
    private val debugRequestAppReview = mock<DebugRequestAppReview>()

    private val requestAppReview =
        RequestAppReviewImpl(
            configStore = configStore,
            androidRequestAppReview = { androidRequestAppReview },
            debugRequestAppReview = { debugRequestAppReview },
        )

    @Test
    fun `given config is debug, request app review uses debug implementation`() =
        runTest {
            configStore.write(
                Config.Entry<Config.Value.BooleanValue>(
                    SdkConfigKey.DebugAppReviewPrompts,
                    Config.Value.BooleanValue(true),
                ),
            )
            requestAppReview.invoke()

            verify(debugRequestAppReview).invoke()
            verifyNoInteractions(androidRequestAppReview)
        }

    @Test
    fun `given config is not debug, request app review uses real implementation`() =
        runTest {
            configStore.write(
                Config.Entry<Config.Value.BooleanValue>(
                    SdkConfigKey.DebugAppReviewPrompts,
                    Config.Value.BooleanValue(false),
                ),
            )

            requestAppReview.invoke()

            verify(androidRequestAppReview).invoke()
            verifyNoInteractions(debugRequestAppReview)
        }
}
