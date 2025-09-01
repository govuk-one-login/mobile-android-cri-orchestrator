package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.createGraphFactory
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
    createGraphFactory<BaseCriOrchestratorComponent.Factory>().create(
        baseSingletonComponent = singletonComponent as BaseCriOrchestratorSingletonComponent,
        activity = activity,
        context = context,
    ) as CriOrchestratorComponent
