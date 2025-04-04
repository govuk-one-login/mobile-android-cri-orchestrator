package uk.gov.onelogin.criorchestrator.sdk.internal

import android.content.Context
import uk.gov.onelogin.criorchestrator.features.config.internal.ConfigComponent
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSingletonComponent

/**
 * Creates instance of [BaseCriOrchestratorComponent].
 *
 * @param singletonComponent The singleton component instance.
 * @param activityContext activity context.
 * @return An instance of [CriOrchestratorComponent].
 */
fun createCriOrchestratorComponent(
    singletonComponent: CriOrchestratorSingletonComponent,
    activityContext: Context,
): CriOrchestratorComponent =
    DaggerMergedBaseCriOrchestratorComponent.factory().create(
        baseSingletonComponent = singletonComponent as BaseCriOrchestratorSingletonComponent,
        configComponent = singletonComponent as ConfigComponent,
        sessionComponent = singletonComponent as SessionComponent,
        context = activityContext,
    )
