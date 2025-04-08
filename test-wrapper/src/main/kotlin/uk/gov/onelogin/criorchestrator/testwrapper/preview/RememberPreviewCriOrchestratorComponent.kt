package uk.gov.onelogin.criorchestrator.testwrapper.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.logging.testdouble.analytics.FakeAnalyticsLogger
import uk.gov.onelogin.criorchestrator.sdk.publicapi.CriOrchestratorSdkExt.create
import uk.gov.onelogin.criorchestrator.sdk.publicapi.rememberCriOrchestrator
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import uk.gov.onelogin.criorchestrator.testwrapper.TestWrapperConfig
import uk.gov.onelogin.criorchestrator.testwrapper.network.createStubHttpClient

@Composable
internal fun rememberPreviewCriOrchestratorComponent(): CriOrchestratorComponent =
    rememberCriOrchestrator(
        criOrchestratorSdk =
            CriOrchestratorSdk.create(
                authenticatedHttpClient = createStubHttpClient(),
                analyticsLogger = FakeAnalyticsLogger(),
                initialConfig = TestWrapperConfig.provideConfig(LocalContext.current.resources),
                logger = SystemLogger(),
                applicationContext = LocalContext.current.applicationContext,
            ),
    )
