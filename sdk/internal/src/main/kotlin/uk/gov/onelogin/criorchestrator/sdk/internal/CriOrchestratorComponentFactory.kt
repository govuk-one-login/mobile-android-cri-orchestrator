package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import uk.gov.onelogin.criorchestrator.features.config.internal.ConfigComponent
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionComponent
import uk.gov.onelogin.criorchestrator.features.session.publicapi.RefreshActiveSessionComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSingletonComponent

/**
 * Creates instance of [BaseCriOrchestratorComponent].
 *
 * @param singletonComponent The singleton component instance.
 * @param activity The host [Activity].
 * @param context The closest [Context]. This might be different to [activity].
 * @return An instance of [CriOrchestratorComponent].
 */
fun createCriOrchestratorComponent(
    singletonComponent: CriOrchestratorSingletonComponent,
    activity: Activity,
    context: Context,
): CriOrchestratorComponent =
    DaggerMergedBaseCriOrchestratorComponent.factory().create(
        baseSingletonComponent = singletonComponent as BaseCriOrchestratorSingletonComponent,
        configComponent = singletonComponent as ConfigComponent,
        sessionComponent = singletonComponent as SessionComponent,
        refreshActiveSessionComponent = singletonComponent as RefreshActiveSessionComponent,
        activity = activity,
        context = context,
    )
