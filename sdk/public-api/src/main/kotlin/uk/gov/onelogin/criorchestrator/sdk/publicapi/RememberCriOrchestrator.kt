package uk.gov.onelogin.criorchestrator.sdk.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.sdk.internal.createCriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

/**
 * Creates and remembers the shared state for the Credential Issuer (CRI) Orchestrator SDK.
 *
 * @param authenticatedHttpClient The HTTP client to make all network calls.
 * @return An instance of [CriOrchestratorComponent]
 */
@Composable
fun rememberCriOrchestrator(authenticatedHttpClient: GenericHttpClient): CriOrchestratorComponent =
    remember {
        createCriOrchestratorComponent(
            authenticatedHttpClient = authenticatedHttpClient,
        )
    }
