package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.idchecksdkactivestate

import kotlinx.coroutines.flow.StateFlow

/**
 * Stores whether the ID Check SDK is active (in progress) or inactive (not in progress). This is required for One Login
 * to manage their local authentication flows correctly.
 */
interface IdCheckSdkActiveStateStore {
    fun read(): StateFlow<Boolean>

    fun setActive()

    fun setInactive()
}
