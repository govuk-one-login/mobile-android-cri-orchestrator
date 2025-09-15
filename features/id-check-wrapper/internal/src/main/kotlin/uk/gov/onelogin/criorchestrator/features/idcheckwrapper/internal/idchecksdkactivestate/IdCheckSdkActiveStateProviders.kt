package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import dev.zacsweers.metro.ContributesTo
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

@ContributesTo(CriOrchestratorAppScope::class)
fun interface IdCheckSdkActiveStateProviders {
    fun idCheckSdkActiveStateStore(): IdCheckSdkActiveStateStore
}
