package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import dev.zacsweers.metro.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate.IsIdCheckSdkActive
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

@ContributesBinding(CriOrchestratorAppScope::class)
class IsIdCheckSdkActiveImpl(
    private val idCheckSdkActiveStateStore: IdCheckSdkActiveStateStore,
) : IsIdCheckSdkActive {
    override fun invoke(): Boolean = idCheckSdkActiveStateStore.read().value
}
