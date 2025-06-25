package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate.IsIdCheckSdkActive
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorSingletonScope::class)
class IsIdCheckSdkActiveImpl
    @Inject
    constructor(
        private val idCheckSdkActiveStateStore: IdCheckSdkActiveStateStore,
    ) : IsIdCheckSdkActive {
        override fun invoke(): Boolean = idCheckSdkActiveStateStore.read().value
    }
