package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.idchecksdkactivestate

import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate.IdCheckSdkActiveStateStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(CriOrchestratorSingletonScope::class, boundType = IdCheckSdkActiveStateStore::class)
class InMemoryIdCheckSdkActiveStateStore
    @Inject
    constructor(
        private val logger: Logger,
    ) : IdCheckSdkActiveStateStore,
        LogTagProvider {
        private val idCheckSdkActiveState: MutableStateFlow<Boolean> = MutableStateFlow(false)

        override fun read(): StateFlow<Boolean> {
            logger.debug(tag, "Reading state ${idCheckSdkActiveState.value} from ID Check SDK active state store")
            return idCheckSdkActiveState.asStateFlow()
        }

        private fun write(value: Boolean) {
            logger.debug(tag, "Writing state $value to ID Check SDK active state store")
            idCheckSdkActiveState.value = value
        }

        override fun setActive() = write(true)

        override fun setInactive() = write(false)
    }
