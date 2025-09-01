package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk

@ContributesTo(CriOrchestratorSingletonScope::class)
fun interface IsIdCheckSdkActiveComponent {
    fun isIdCheckSdkActive(): IsIdCheckSdkActive
}

/**
 * Get the [IsIdCheckSdkActive] use case.
 */
val CriOrchestratorSdk.isIdCheckSdkActive: IsIdCheckSdkActive get() =
    (this.component as IsIdCheckSdkActiveComponent).isIdCheckSdkActive()
