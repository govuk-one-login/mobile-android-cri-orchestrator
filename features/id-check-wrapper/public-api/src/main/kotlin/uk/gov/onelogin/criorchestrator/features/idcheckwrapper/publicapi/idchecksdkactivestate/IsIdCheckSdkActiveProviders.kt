package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@ContributesTo(CriOrchestratorAppScope::class)
fun interface IsIdCheckSdkActiveProviders {
    fun isIdCheckSdkActive(): IsIdCheckSdkActive
}

/**
 * Get the [IsIdCheckSdkActive] use case.
 */
val CriOrchestratorSdk.isIdCheckSdkActive: IsIdCheckSdkActive get() =
    (this.appGraph as IsIdCheckSdkActiveProviders).isIdCheckSdkActive()
