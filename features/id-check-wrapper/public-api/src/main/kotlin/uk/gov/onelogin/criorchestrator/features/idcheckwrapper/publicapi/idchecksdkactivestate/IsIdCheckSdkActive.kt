package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.idchecksdkactivestate

fun interface IsIdCheckSdkActive {
    operator fun invoke(): Boolean
}
