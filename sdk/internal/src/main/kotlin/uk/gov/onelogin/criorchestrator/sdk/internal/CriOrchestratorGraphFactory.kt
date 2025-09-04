package uk.gov.onelogin.criorchestrator.sdk.internal

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.createGraphFactory
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorAppGraph
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorGraph

/**
 * Creates instance of [BaseCriOrchestratorGraph].
 *
 * @param appGraph The app graph instance.
 * @param activity The host [Activity].
 * @param context The closest [Context]. This might be different to [activity].
 * @return An instance of [CriOrchestratorGraph].
 */
fun createCriOrchestratorGraph(
    appGraph: CriOrchestratorAppGraph,
    activity: Activity,
    context: Context,
): CriOrchestratorGraph =
    createGraphFactory<BaseCriOrchestratorGraph.Factory>().create(
        baseAppGraph = appGraph as BaseCriOrchestratorAppGraph,
        activity = activity,
        context = context,
    ) as CriOrchestratorGraph
