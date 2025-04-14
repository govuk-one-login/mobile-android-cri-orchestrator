package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import uk.gov.idcheck.sdk.IdCheckSdkActivityResultContract
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model.ExitStateOption

data class IdCheckSdkActivityResultContractParameters(
    private val logger: Logger,
    private val stubExitState: ExitStateOption,
) {
    fun toActivityResultContract() =
        IdCheckSdkActivityResultContract(
            stubExitState = stubExitState.exitState,
            logger = logger,
        )
}
