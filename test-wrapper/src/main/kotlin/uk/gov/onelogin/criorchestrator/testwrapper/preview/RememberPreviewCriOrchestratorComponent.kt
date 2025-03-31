package uk.gov.onelogin.criorchestrator.testwrapper.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.testwrapper.TestWrapperConfig
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient

@Composable
internal fun rememberPreviewCriOrchestratorComponent() =
    rememberCriOrchestrator(
        authenticatedHttpClient = createHttpClient(LocalContext.current.resources, "mock_subject_token"),
        analyticsLogger = FakeAnalyticsLogger(),
        initialConfig = TestWrapperConfig.provideConfig(LocalContext.current.resources),
        logger = SystemLogger(),
    )
