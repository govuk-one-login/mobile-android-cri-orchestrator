package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import com.squareup.anvil.annotations.ContributesTo
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Singleton

@Singleton
@ContributesTo(CriOrchestratorSingletonScope::class)
interface IdCheckSdkActiveStateComponent {
    fun idCheckSdkActiveStateStore(): IdCheckSdkActiveStateStore
}
