package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorSdk
import javax.inject.Singleton

@Singleton
@ContributesTo(CriOrchestratorSingletonScope::class)
fun interface IsIdCheckSdkActiveComponent {
    fun isIdCheckSdkActive(): IsIdCheckSdkActive
}

/**
 * Get the [RefreshActiveSession] use case.
 */
val CriOrchestratorSdk.isIdCheckSdkActive: IsIdCheckSdkActive get() =
    (this.component as IsIdCheckSdkActiveComponent).isIdCheckSdkActive()
