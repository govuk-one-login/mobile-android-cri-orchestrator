package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate

/**
 * Publicly accessible use case that checks whether the ID Check SDK journey is active (in progress) or inactive (not
 * in progress)
 */
fun interface IsIdCheckSdkActive {
    operator fun invoke(): Boolean
}
