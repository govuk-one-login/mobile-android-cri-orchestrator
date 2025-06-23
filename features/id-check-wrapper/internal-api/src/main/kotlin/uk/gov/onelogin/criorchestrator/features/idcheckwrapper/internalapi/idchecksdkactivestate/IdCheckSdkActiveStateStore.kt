package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate

import kotlinx.coroutines.flow.StateFlow

interface IdCheckSdkActiveStateStore {
    fun read(): StateFlow<Boolean>

    fun setActive()

    fun setInactive()
}