package uk.gov.onelogin.criorchestrator.sdk.publicapi

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import uk.gov.onelogin.criorchestrator.sdk.internal.createCriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

/**
 * Creates and remembers the shared state for the Credential Issuer (CRI) Orchestrator SDK.
 *
 * @param criOrchestratorSdk The SDK instance (from [CriOrchestratorSdk.Companion.create]).
 * @return An instance of [CriOrchestratorComponent]
 */
@Composable
fun rememberCriOrchestrator(criOrchestratorSdk: CriOrchestratorSdk): CriOrchestratorComponent {
    val context = LocalContext.current
    val activity = LocalActivity.current ?: error("No activity found")
    return remember {
        createCriOrchestratorComponent(
            singletonComponent = criOrchestratorSdk.component,
            activity = activity,
            context = context,
        )
    }
}
